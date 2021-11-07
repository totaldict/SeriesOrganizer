package com.example.seriesorganizer.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.seriesorganizer.dao.IDAOTvSeries
import com.example.seriesorganizer.model.CTvSeries
import com.example.seriesorganizer.utils.converters.CConverters

@Database(
    entities = [CTvSeries::class],
    version = 1
)

@TypeConverters(CConverters::class)
abstract class CDatabase : RoomDatabase() {
    abstract fun daoTvSeries(): IDAOTvSeries

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CDatabase? = null

        fun getDatabase(context: Context): CDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            // прочерка чтобы была одна БД создана
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CDatabase::class.java,
                    "database.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}