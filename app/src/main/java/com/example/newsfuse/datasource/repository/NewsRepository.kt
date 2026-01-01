package com.example.newsfuse.datasource.repository


import com.example.newsfuse.datasource.data.News
import com.example.newsfuse.datasource.data.toNews
import com.example.newsfuse.datasource.local.db.dao.FeedsDao
import com.example.newsfuse.datasource.local.db.dao.NewsDao
import com.example.newsfuse.datasource.local.db.entity.NewsEntity
import com.example.newsfuse.datasource.local.db.entity.NewsFeedEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepository(
    private val newsDao: NewsDao,
    private val feedsDao: FeedsDao,
) {
    val getLatestNews: Flow<Set<News>> = newsDao.getAllNewsEntities()
        .map { newsEntityList ->
            newsEntityList?.map { newsEntity ->
                toNews(newsEntity)
            }?.toSet() ?: emptySet()
        }

    fun getNewsById(newsId: String): Flow<NewsEntity?> = newsDao.getNewsEntityById(newsId)

    fun getSelectedFeed(): Flow<NewsFeedEntity?> = feedsDao.getSelectedFeed()
}