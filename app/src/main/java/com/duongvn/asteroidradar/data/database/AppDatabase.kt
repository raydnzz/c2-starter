package com.duongvn.asteroidradar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duongvn.asteroidradar.data.database.AppDatabase.Companion.DB_VERSION
import com.duongvn.asteroidradar.data.database.dao.AsteroidDao
import com.duongvn.asteroidradar.data.database.entity.Asteroid

@Database(entities = [Asteroid::class], version = DB_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAsteroidDao(): AsteroidDao

    companion object {
        const val DB_VERSION = 1
        private const val DB_NAME = "AppDatabase"

        @Volatile
        var INSTANCE: AppDatabase? = null
            private set

        @Synchronized
        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .build().also {
                    INSTANCE = it
                }
        }
    }
}