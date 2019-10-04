package com.syanko.simonis.domain.repository

import com.syanko.simonis.domain.entity.User

interface AuthManager {
    suspend fun saveLoggedInUser(user: User): Long
    suspend fun getActiveUser(): User
    suspend fun isUserLoggedIn(): Int
    suspend fun deleteUser(): Int
    suspend fun getToken(): String
}