package com.syanko.simonis.platform.repository

import com.syanko.simonis.domain.entity.User
import com.syanko.simonis.domain.repository.AuthManager
import com.syanko.simonis.platform.db.DaoProvider
import com.syanko.simonis.platform.db.entity.Profile

class AuthManagerImpl(private val daoProvider: DaoProvider) : AuthManager {
    override suspend fun saveLoggedInUser(user: User): Long {
        val loggedInProfile = user.asProfile()
        return daoProvider.profileDao()
            .saveLoggedInProfile(loggedInProfile)
    }

    override suspend fun getActiveUser(): User {
        return daoProvider.profileDao()
            .getLoggedInProfile()
            .mapEntityToUser()
    }

    override suspend fun deleteUser(): Int {
        return daoProvider.profileDao()
            .deleteLoggedInProfile()
    }

    override suspend fun getToken(): String {
        return daoProvider
            .profileDao()
            .getLoggedInProfileToken()
    }

    override suspend fun isUserLoggedIn(): Int {
        return daoProvider.profileDao()
            .isProfileLoggedIn()
    }
}

private fun User.asProfile(): Profile {
    return Profile(
        id,
        firstName,
        lastName,
        nrp,
        phone,
        email,
        name,
        password,
        company,
        token
    )
}

private fun Profile.mapEntityToUser(): User {
    return User(
        id,
        userName,
        password,
        firstName,
        lastName,
        nrp,
        phone,
        email,
        company,
        "",
        token
    )
}
