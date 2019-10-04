package com.syanko.simonis.domain.entity

data class FormValueEntity(
    val formResultId: Int,
    val formFieldSectionId: Int,
    val type: Int,
    val value: String
)