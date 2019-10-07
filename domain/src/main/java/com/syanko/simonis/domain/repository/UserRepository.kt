package com.syanko.simonis.domain.repository

import com.syanko.simonis.domain.entity.User

interface UserRepository {
    suspend fun login(username: String, password: String): User

    suspend fun isUserLoggedIn(): Int

    suspend fun getActiveUser(): User

    suspend fun saveLoggedInUser(user: User): Long
    suspend fun deleteCurrentUserIfAny(): Int
    suspend fun getToken(): String
}