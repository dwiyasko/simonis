package com.syanko.simonis.platform.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val nrp: String,
    @ColumnInfo(defaultValue = "0")
    val phone: String,
    val email: String,
    val userName: String,
    val password: String,
    val company: String,
    val token: String
)