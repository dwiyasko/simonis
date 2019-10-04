package com.syanko.simonis.domain.entity

data class Menu(
    val id: Long,
    val name: String,
    val parent: String,
    val isLastChild: Boolean,
    val children: List<Menu>
)