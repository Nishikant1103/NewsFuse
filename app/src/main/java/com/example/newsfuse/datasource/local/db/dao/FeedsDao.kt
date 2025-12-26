package com.example.newsfuse.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsfuse.datasource.local.db.entity.NewsFeedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFeedEntities(newsFeedEntity: NewsFeedEntity)

    @Query("Select * from NewsFeedEntity")
    fun getAllFeedEntities(): Flow<List<NewsFeedEntity>>
}