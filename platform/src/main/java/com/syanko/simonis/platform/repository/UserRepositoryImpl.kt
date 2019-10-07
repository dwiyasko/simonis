package com.syanko.simonis.platform.repository

import com.syanko.simonis.domain.entity.User
import com.syanko.simonis.domain.repository.AuthManager
import com.syanko.simonis.domain.repository.UserRepository
import com.syanko.simonis.platform.api.LoginApi
import com.syanko.simonis.platform.response.mapProfileToUser

class UserRepositoryImpl(
    private val loginApi: LoginApi,
    private val authManager: AuthManager
) : UserRepository {
    override suspend fun login(username: String, password: String): User {
        return loginApi.login(username, password)
            .mapProfileToUser(withPassword = password)
    }

    override suspend fun saveLoggedInUser(user: User): Long {
        return authManager.saveLoggedInUser(user)
    }

    override suspend fun isUserLoggedIn(): Int {
        return authManager.isUserLoggedIn()
    }

    override suspend fun getActiveUser(): User {
        return authManager.getActiveUser()
    }

    override suspend fun deleteCurrentUserIfAny(): Int {
        return authManager.deleteUser()
    }

    override suspend fun getToken(): String {
        return authManager.getToken()
    }
}



