package com.example.newsfuse.datasource.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsfuse.datasource.local.db.dao.FeedsDao
import com.example.newsfuse.datasource.local.db.dao.NewsDao
import com.example.newsfuse.datasource.local.db.entity.NewsEntity
import com.example.newsfuse.datasource.local.db.entity.NewsFeedEntity

@Database(
    entities = [NewsEntity::class, NewsFeedEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun feedsDao(): FeedsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        fun getDatabase(context: Context): NewsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_database"
                )
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
