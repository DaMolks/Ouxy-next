package com.damolks.ouxynext.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.damolks.ouxynext.core.data.converters.DateConverter
import com.damolks.ouxynext.core.data.converters.MapConverter
import com.damolks.ouxynext.core.data.dao.EventLogDao
import com.damolks.ouxynext.core.data.dao.ModuleStateDao
import com.damolks.ouxynext.core.data.entities.EventLogEntity
import com.damolks.ouxynext.core.data.entities.ModuleStateEntity

@Database(
    entities = [
        ModuleStateEntity::class,
        EventLogEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateConverter::class, MapConverter::class)
abstract class OuxyDatabase : RoomDatabase() {

    abstract fun moduleStateDao(): ModuleStateDao
    abstract fun eventLogDao(): EventLogDao

    companion object {
        private const val DATABASE_NAME = "ouxy_db"

        @Volatile
        private var instance: OuxyDatabase? = null

        fun getInstance(context: Context): OuxyDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): OuxyDatabase {
            return Room.databaseBuilder(context, OuxyDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}