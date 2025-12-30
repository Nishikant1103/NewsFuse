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

            val feedUrl = feedsDao
                .getSelectedFeed()        // Flow<NewsFeedEntity?>
                .first()
                ?.feedUrl

            if (feedUrl.isNullOrBlank()) {
                return Result.failure()
            }

            val newsResult = newsDataSource.getNewsFromFeedUrl(feedUrl)

            if (!newsResult.isSuccess) return Result.failure()

            val news = newsResult.getOrNull().orEmpty()

            val entities = news.map { item ->
                NewsEntity(
                    id = item.id,
                    datePosted = item.datePosted,
                    newsTitle = item.newsTitle,
                    newsDescription = item.newsDescription,
                    newsLink = item.newsLink,
                    newsImage = item.newsImageLink
                )
            }

            newsDao.refreshNewsEntities(entities)

            Result.success()

        } catch (e: Exception) {

            val errorData = Data.Builder()
                .putString("error_message", e.message)
                .build()

            Result.failure(errorData)
        }
    }
}