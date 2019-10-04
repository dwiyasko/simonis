package com.syanko.simonis.platform.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "form_result")
data class FormResult(
    val formId: Int,
    val sectionId: Int,
    val userId: Int,
    val inspectionId: Int,
    val giId: Int,
    val voltId: Int,
    val bayId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

