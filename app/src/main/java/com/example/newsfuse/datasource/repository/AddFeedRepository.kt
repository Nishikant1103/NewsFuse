package com.example.newsfuse.datasource.repository

import com.example.newsfuse.datasource.local.db.dao.FeedsDao
import com.example.newsfuse.datasource.local.db.entity.NewsFeedEntity
import com.example.newsfuse.datasource.remote.NewsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddFeedRepository(
    private val newsDataSource: NewsDataSource,
    private val feedsDao: FeedsDao,) {

    suspend fun verifyNewsFeedLink(url: String): Boolean {
        var isvalid = false
        withContext(Dispatchers.IO) {
            val result = newsDataSource.getNewsFromFeedUrl(url)
            if (result.isSuccess) {
                isvalid = result.getOrNull()?.isNotEmpty() ?: false
            } else if (result.isFailure) {
                isvalid = false
            }
        }

        return isvalid
    }

    suspend fun addFeedToLocalStorage(textName: String, textUrl: String) {
        withContext(Dispatchers.IO) {
            feedsDao.insertFeedEntities(
                NewsFeedEntity(
                    feedName = textName,
                    feedUrl = textUrl
                )
            )
        }
    }
}