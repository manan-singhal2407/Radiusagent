package com.radiusagent.assignment.data.cache.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.radiusagent.assignment.data.cache.database.model.HomeCache
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(homeCache: HomeCache): Long

    @Query("SELECT * FROM HomeCache")
    fun getHomePageData(): Flow<List<HomeCache>>
}