package com.radiusagent.assignment.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.radiusagent.assignment.data.cache.database.dao.UserDao
import com.radiusagent.assignment.data.cache.database.model.UserCache

@Database(entities = [UserCache::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME: String = "AppDatabase"
    }
}