package com.radiusagent.assignment.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.radiusagent.assignment.data.cache.database.dao.FacilityDao
import com.radiusagent.assignment.data.cache.database.model.FacilityCache

@Database(entities = [FacilityCache::class], version = 1, exportSchema = false)
@TypeConverters(FacilityCache.FacilityListConverter::class, FacilityCache.ExclusionListConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun facilityDao(): FacilityDao

    companion object {
        const val DATABASE_NAME: String = "AppDatabase"
    }
}