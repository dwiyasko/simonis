package com.syanko.simonis.platform.db

import com.syanko.simonis.platform.db.dao.FormDao
import com.syanko.simonis.platform.db.dao.ProfileDao
import com.syanko.simonis.platform.db.dao.ValueDao

interface DaoProvider {
    fun profileDao(): ProfileDao
    fun formDao(): FormDao
//    fun formValueDao(): ValueDao
}

class DaoProviderImpl(private val database: SimonisDatabase) : DaoProvider {
    override fun profileDao(): ProfileDao {
        return database.profileDao()
    }

    override fun formDao(): FormDao {
        return database.formDao()
    }

//    override fun formValueDao(): ValueDao {
//        return database.formValueDao()
//    }

}