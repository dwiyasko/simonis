package com.syanko.simonis.platform.response

data class GroupResponse(
    val id: Int,
    val parent: String,
    val name: String,
    val isLastChild: Boolean,
    val children: List<GroupResponse>
)