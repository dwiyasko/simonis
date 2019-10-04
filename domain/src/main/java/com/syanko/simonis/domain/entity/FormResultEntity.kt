package com.syanko.simonis.domain.entity

data class FormResultEntity(
    val userId: Int,
    val inspectionId: Int,
    val formId: Int,
    val substationId: Int,
    val voltageId: Int,
    val bayId: Int
)