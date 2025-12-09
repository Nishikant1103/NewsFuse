package com.example.newsfuse.datasource.repository


import com.example.newsfuse.datasource.data.News
import com.example.newsfuse.datasource.local.db.dao.NewsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepository(
    private val newsDao: NewsDao
) {
    val getLatestNews: Flow<Set<News>> = newsDao.getAllNewsEntities()
        .map { newsEntityList ->
            newsEntityList.map { newsEntity ->
                News(
                    id = newsEntity.id,
                    datePosted = newsEntity.datePosted,
                    newsTitle = newsEntity.newsTitle,
                    newsDescription = newsEntity.newsDescription,
                    newsLink = newsEntity.newsLink,
                    newsImage = newsEntity.newsImage
                )
            }.toSet()
        }
}