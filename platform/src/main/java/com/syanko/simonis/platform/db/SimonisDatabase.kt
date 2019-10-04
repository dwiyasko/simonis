package com.syanko.simonis.platform.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.syanko.simonis.platform.db.dao.FormDao
import com.syanko.simonis.platform.db.dao.ProfileDao
import com.syanko.simonis.platform.db.dao.ValueDao
import com.syanko.simonis.platform.db.entity.FormResult
import com.syanko.simonis.platform.db.entity.Profile
import com.syanko.simonis.platform.db.entity.Value

@Database(
    entities = [Profile::class, FormResult::class, Value::class],
    exportSchema = false,
    version = 4
)
abstract class SimonisDatabase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao
    abstract fun formDao(): FormDao
    abstract fun formValueDao(): ValueDao

    companion object {
        private val LOCK = Any()
        private const val DATABASE_NAME = "simonis_db"
        private var sInstance: SimonisDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE profile ADD COLUMN phone TEXT NOT NULL DEFAULT '0' ")
            }

        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                val queryFormResult =
                    "CREATE TABLE `form_result` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `formId` INTEGER NOT NULL, " +
                            "`sectionId` INTEGER NOT NULL, `userId` INTEGER NOT NULL, `inspectionId` INTEGER NOT NULL, `giId` INTEGER NOT NULL, " +
                            "`voltId` INTEGER NOT NULL, `bayId` INTEGER NOT NULL, PRIMARY KEY(`id`) AUTOINCREMENT)"
                val queryFormValue =
                    "CREATE TABLE `form_value` (`valueId` INTEGER NOT NULL, `formResultId` INTEGER NOT NULL, `fieldSectionId` INTEGER NOT NULL, " +
                            "`value` TEXT NOT NULL, `type` INTEGER NOT NULL, PRIMARY KEY(`valueId`), FOREIGN KEY(`formResultId`) " +
                            "REFERENCES `form_result`(`id`) ON DELETE CASCADE)"
                val queryFormIndex = "CREATE INDEX form_index on `form_value`(`formResultId`)"
                database.execSQL(queryFormResult)
                database.execSQL(queryFormValue)
                database.execSQL(queryFormIndex)
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                val createNewTable =
                    "CREATE TABLE `new_form_result` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `formId` INTEGER NOT NULL" +
                            ", `sectionId` INTEGER NOT NULL, `userId` INTEGER NOT NULL, `inspectionId` INTEGER NOT NULL, `giId` INTEGER NOT NULL, " +
                            "`voltId` INTEGER NOT NULL, `bayId` INTEGER NOT NULL)"
                val insertTable = "INSERT INTO `new_form_result`(`id`, `formId`, " +
                        "`sectionId`, `userId`, `inspectionId`, `giId`, " +
                        "`voltId`, `bayId`) SELECT * FROM `form_result`"
                val dropTable = "DROP TABLE `form_result`"
                val renameTable = "ALTER TABLE `new_form_result` RENAME TO `form_result`"

                database.execSQL(createNewTable)
                database.execSQL(insertTable)
                database.execSQL(dropTable)
                database.execSQL(renameTable)
            }
        }


        fun getInstance(context: Context): SimonisDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder(
                        context.applicationContext,
                        SimonisDatabase::class.java, DATABASE_NAME
                    ).addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                        .build()
                }
            }
            return sInstance
        }
    }
}

