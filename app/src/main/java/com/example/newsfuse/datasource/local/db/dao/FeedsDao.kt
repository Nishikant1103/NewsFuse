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

    @Query("Select * from NewsFeedEntity WHERE selected = true")
    fun getSelectedFeed(): Flow<NewsFeedEntity?>

    @Query("DELETE from NewsFeedEntity WHERE id = :id")
    suspend fun deleteFeedById(id: Int)

    @Query("Select * from NewsFeedEntity WHERE id = :id")
    suspend fun getFeedById(id: Int): NewsFeedEntity

    @Query("""
        UPDATE NewsFeedEntity
        SET selected = CASE WHEN id = :id THEN 1 ELSE 0 END
    """)
    suspend fun updateSelection(id: Int)


}