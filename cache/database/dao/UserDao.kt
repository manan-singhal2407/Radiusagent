package com.radiusagent.assignment.data.cache.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.radiusagent.assignment.data.cache.database.model.UserCache
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(userCache: UserCache): Long

    @Query("SELECT * FROM UserCache")
    fun getUser(): Flow<List<UserCache>>
}