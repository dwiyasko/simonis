package com.syanko.simonis.platform.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syanko.simonis.platform.db.entity.Profile

@Dao
interface ProfileDao {
    @Query("Select id from profile limit 1")
    fun isProfileLoggedIn(): Int

    @Query("Select token from profile limit 1")
    fun getLoggedInProfileToken(): String

    @Query("Select * from profile limit 1")
    fun getLoggedInProfile(): Profile

    @Query("Delete from profile")
    fun deleteLoggedInProfile(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveLoggedInProfile(profile: Profile): Long
}