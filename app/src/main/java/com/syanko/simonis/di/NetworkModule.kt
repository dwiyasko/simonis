package com.syanko.simonis.di

import com.google.gson.GsonBuilder
import com.syanko.simonis.domain.repository.AuthManager
import com.syanko.simonis.platform.api.LoginApi
import com.syanko.simonis.platform.response.mapProfileToUser
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://simonis.woicaksono.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideClient(simonisInterceptor: SimonisInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .addInterceptor(simonisInterceptor.createRequestInterceptor())
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideSimonisInterceptor(
        authManager: AuthManager
    ): SimonisInterceptor {
        return SimonisInterceptor(authManager)
    }
}

class SimonisInterceptor(
    private val authManager: AuthManager
) {

    private suspend fun getTokenFromDb(): String {
        return withContext(Dispatchers.IO) {
            authManager.getToken()
        }
    }

    private suspend fun refreshToken(): String {
        return withContext(Dispatchers.IO) {
            val activeUser = authManager.getActiveUser()

            val retrofitRefresh = getRetrofitRefresh()

            val loginApi = retrofitRefresh.create(LoginApi::class.java)
            val newLoggedInUser = loginApi.login(activeUser.name, activeUser.password)
                .mapProfileToUser(withPassword = activeUser.password)
            authManager.saveLoggedInUser(newLoggedInUser)

            newLoggedInUser.token
        }
    }

    private fun getRetrofitRefresh(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://simonis.woicaksono.com/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    fun createRequestInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()

                val url = request.url

                if (url.encodedPathSegments[1] != "login") {
                    val token = runBlocking {
                        getTokenFromDb()
                    }

                    val builder = request.newBuilder()
                    builder.addHeader("accept", "application/json")
                    request = addHeaderTokenAndBuild(builder, token)

                    val response = chain.proceed(request)

                    when (response.code) {
                        401 -> {
                            synchronized(request) {
                                val newToken = runBlocking {
                                    refreshToken()
                                }
                                request = addHeaderTokenAndBuild(builder, newToken)
                                return chain.proceed(request)
                            }
                        }
                        else -> return response
                    }
                }
                return chain.proceed(request)
            }

            private fun addHeaderTokenAndBuild(builder: Request.Builder, token: String): Request {
                builder.removeHeader("Authorization")
                builder.addHeader("Authorization", "Bearer $token")
                return builder.build()
            }
        }
    }
}
