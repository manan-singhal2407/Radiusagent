package com.radiusagent.assignment.data.cache.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.radiusagent.assignment.data.cache.database.model.FacilityCache
import kotlinx.coroutines.flow.Flow

@Dao
interface FacilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(facilityCache: FacilityCache): Long

    @Query("SELECT * FROM FacilityCache")
    fun getHomePageData(): Flow<List<FacilityCache>>
}