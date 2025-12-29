package com.example.newsfuse.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.example.newsfuse.datasource.data.News
import com.example.newsfuse.datasource.local.db.NewsDatabase
import com.example.newsfuse.datasource.local.db.dao.FeedsDao
import com.example.newsfuse.datasource.local.db.dao.NewsDao
import com.example.newsfuse.datasource.local.db.entity.NewsEntity
import com.example.newsfuse.datasource.remote.NewsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext


class NewsProviderWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    private val newsDao: NewsDao =
        NewsDatabase.getDatabase(context).newsDao()

    private val feedsDao: FeedsDao =
        NewsDatabase.getDatabase(context).feedsDao()
    private val newsDataSource: NewsDataSource =
        NewsDataSource()

    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                val rssFeedUrl = feedsDao
                    .getSelectedFeed()   // Flow<List<Feed>>
                    .first()              // suspend until first value
                    .feedUrl

                val newsResult = newsDataSource.getNewsFromFeedUrl(rssFeedUrl)

                if (newsResult.isSuccess) {
                    val newsSet = newsResult.getOrNull()
                    newsSet?.let { news: Set<News> ->
                        // Convert Set<News> to List<NewsEntity>
                        val newsEntityList = news.map { newsItem ->
                            NewsEntity(
                                id = newsItem.id,
                                datePosted = newsItem.datePosted,
                                newsTitle = newsItem.newsTitle,
                                newsDescription = newsItem.newsDescription,
                                newsLink = newsItem.newsLink,
                                newsImage = newsItem.newsImageLink
                            )
                        }
                        newsDao.refreshNewsEntities(newsEntityList)
                    }
                    Result.success()
                } else {
                    Result.failure()
                }
            }
        } catch (e: Exception) {
            val errorData = Data.Builder()
                .putString("error_message", e.message)
                .build()
            Result.failure(errorData)
        }
    }
}