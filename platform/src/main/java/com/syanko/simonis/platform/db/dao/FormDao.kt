package com.syanko.simonis.platform.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.syanko.simonis.platform.db.entity.FormResult

@Dao
interface FormDao {
    @Insert
    suspend fun saveFormSession(formResult: FormResult): Long

    @Query("DELETE FROM form_result WHERE id=:id")
    suspend fun deleteFormSession(id: Long): Int

    @Query("SELECT * FROM form_result INNER JOIN form_value on formResultId=id WHERE id = :id")
    suspend fun getFormResultByForm(id: Int): FormResult
}