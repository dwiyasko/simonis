package com.syanko.simonis.domain.entity

data class User(
    val id: Int,
    val name: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val nrp: String,
    val phone: String,
    val email: String,
    val company: String,
    val privilege: String,
    val token: String
)
