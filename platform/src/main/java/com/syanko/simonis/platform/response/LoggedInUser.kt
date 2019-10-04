package com.syanko.simonis.platform.response

import com.google.gson.annotations.SerializedName

data class LoggedInUser(
    val id: Int,

    @SerializedName("firstname")
    val firstName: String,

    @SerializedName("lastname")
    val lastName: String,

    val nrp: String,
    val phone: String,
    val email: String,

    @SerializedName("username")
    val name: String,
    val company: String
)