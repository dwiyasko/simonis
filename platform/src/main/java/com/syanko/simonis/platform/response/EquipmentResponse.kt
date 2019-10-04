package com.syanko.simonis.platform.response

import com.google.gson.annotations.SerializedName

data class EquipmentResponse(
    val data: List<Equipment>
)

data class Equipment(
    val id: Int,
    @SerializedName("group_id")
    val groupId: Int,
    val name: String,
    @SerializedName("breadcrumbs")
    val breadCrumbs: String
)