package com.syanko.simonis.platform.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.syanko.simonis.platform.db.entity.Value

@Dao
interface ValueDao {
    @Insert
    suspend fun saveFormValue(formValue: List<Value>): Long
}