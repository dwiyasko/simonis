package com.syanko.simonis.domain.entity

data class InspectionSchedule(
    val id: Int,
    val title: String,
    val description: String,
    val period: String,
    val time: String,
    val equipmentName: String
)