package com.syanko.simonis.di

import com.syanko.simonis.platform.api.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun provideLoginApi(retrofit: Retrofit): LoginApi {
        return retrofit.create(LoginApi::class.java)
    }

    @Provides
    fun provideGroupApi(retrofit: Retrofit): GroupApi {
        return retrofit.create(GroupApi::class.java)
    }

    @Provides
    fun provideInspectionApi(retrofit: Retrofit): InspectionApi {
        return retrofit.create(InspectionApi::class.java)
    }

    @Provides
    fun provideFormApi(retrofit: Retrofit): FormApi {
        return retrofit.create(FormApi::class.java)
    }

    @Provides
    fun provideEquipmentApi(retrofit: Retrofit): EquipmentApi {
        return retrofit.create(EquipmentApi::class.java)
    }
}
