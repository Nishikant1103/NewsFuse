package com.example.newsfuse.datasource.repository


import com.example.newsfuse.core.utility.TimeFormatter
import com.example.newsfuse.datasource.data.News
import com.example.newsfuse.datasource.local.db.dao.NewsDao
import com.example.newsfuse.datasource.local.db.entity.NewsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepository(
    private val newsDao: NewsDao,
    private val timeFormatter: TimeFormatter
) {
    val getLatestNews: Flow<Set<News>> = newsDao.getAllNewsEntities()
        .map { newsEntityList ->
            newsEntityList.map { newsEntity ->
                toNews(newsEntity)
            }.toSet()
        }

    suspend fun getNewsById(newsId: String): News = toNews(newsDao.getNewsEntityById(newsId))

    private fun toNews(entity: NewsEntity): News = News(
        id = entity.id,
        datePosted = timeFormatter.getFormattedTime(
            inputFormat = "EEE, dd MMM yyyy HH:mm:ss Z",
            outputFormat = "dd.MM.yyyy HH:mm",
            entity.datePosted
        ) ?: entity.datePosted,
        newsTitle = entity.newsTitle,
        newsDescription = entity.newsDescription,
        newsLink = entity.newsLink,
        newsImage = entity.newsImage
    )
}