package com.syanko.simonis.platform.response

import com.google.gson.annotations.SerializedName
import com.syanko.simonis.domain.entity.User

data class LoginResponse(
    val user: LoggedInUser,

    @SerializedName("access_token")
    val accessToken: String
)

fun LoginResponse.mapProfileToUser(withPassword: String): User {
    return User(
        user.id,
        user.name,
        withPassword,
        user.firstName,
        user.lastName,
        user.nrp,
        user.phone,
        user.email,
        user.company,
        "",
        accessToken
    )
}
