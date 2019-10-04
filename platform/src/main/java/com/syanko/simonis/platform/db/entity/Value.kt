package com.syanko.simonis.platform.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "form_value",
    foreignKeys = [ForeignKey(
        entity = FormResult::class,
        parentColumns = ["id"],
        childColumns = ["formResultId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["formResultId"], name = "form_index", unique = false)]
)
data class Value(
    val formResultId: Int,
    val fieldSectionId: Int,
    val value: String,
    val type: Int
) {
    @PrimaryKey(autoGenerate = true)
    var valueId: Int = 0
}
