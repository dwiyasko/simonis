package com.syanko.simonis.domain.interactor

import com.syanko.simonis.domain.entity.User
import com.syanko.simonis.domain.repository.UserRepository

interface UserInterActor {
    suspend fun signIn(username: String, password: String): User
    suspend fun isLoggedIn(): Boolean
    suspend fun getActiveUser(): User
    suspend fun logout(): Int
}

class UserInterActorImpl(private val userRepository: UserRepository) : UserInterActor {
    override suspend fun signIn(username: String, password: String): User {
        val loggedInUser = userRepository.login(username, password)
        userRepository.deleteCurrentUserIfAny()
        userRepository.saveLoggedInUser(loggedInUser)

        return loggedInUser
    }

    override suspend fun isLoggedIn(): Boolean {
        return userRepository.isUserLoggedIn() > 0
    }

    override suspend fun getActiveUser(): User {
        return userRepository.getActiveUser()
    }

    override suspend fun logout(): Int {
        return userRepository.deleteCurrentUserIfAny()
    }
}