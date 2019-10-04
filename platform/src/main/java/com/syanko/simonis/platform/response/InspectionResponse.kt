package com.syanko.simonis.platform.response

import com.google.gson.annotations.SerializedName

data class InspectionResponse(
    val data: List<Inspection>
)

data class Inspection(
    val id: Int,
    val title: String,
    val description: String,
    val period: String,
    val time: String,
    @SerializedName("equipmentname")
    val equipmentName: String
)