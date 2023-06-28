package com.radiusagent.assignment.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.radiusagent.assignment.data.cache.database.dao.HomeDao
import com.radiusagent.assignment.data.cache.database.model.HomeCache

@Database(entities = [HomeCache::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun homeDao(): HomeDao

    companion object {
        const val DATABASE_NAME: String = "AppDatabase"
    }
}