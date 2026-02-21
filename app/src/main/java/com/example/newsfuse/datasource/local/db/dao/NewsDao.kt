package com.example.newsfuse.datasource.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.newsfuse.datasource.local.db.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("Select * from NewsEntity")
    fun getAllNewsEntities(): Flow<List<NewsEntity>?>

    @Query("DELETE FROM NewsEntity")
    suspend fun deleteAllNewsEntities()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewsEntities(newsList: List<NewsEntity>)

    @Query("Select * from NewsEntity WHERE id = :newsId")
    fun getNewsEntityById(newsId: Int): Flow<NewsEntity?>

    @Transaction
    suspend fun refreshNewsEntities(newsList: List<NewsEntity>) {
        deleteAllNewsEntities()
        insertNewsEntities(newsList)
    }
}
