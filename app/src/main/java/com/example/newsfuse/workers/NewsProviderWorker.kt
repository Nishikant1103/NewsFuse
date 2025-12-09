package com.example.newsfuse.workers

import com.example.newsfuse.datasource.local.db.dao.NewsDao
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.newsfuse.core.NewsApplication.Companion.RSS_FEED_URL_KEY
import com.example.newsfuse.datasource.remote.NewsDataSource
import com.example.newsfuse.datasource.data.News
import com.example.newsfuse.datasource.local.db.NewsDatabase
import com.example.newsfuse.datasource.local.db.entity.NewsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class NewsProviderWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {
    private val newsDao: NewsDao =
        NewsDatabase.getDatabase(context).newsDao()
    private val newsDataSource: NewsDataSource =
        NewsDataSource()

    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                // Get RSS feed URL from input data or use default
                val rssFeedUrl = inputData.getString(RSS_FEED_URL_KEY)
                    ?: "https://www.thehindu.com/feeder/default.rss" // Default RSS feed URL

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
                                newsImage = newsItem.newsImage
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
            Log.e("Nishi", "ERROR in doWork", e)
            Result.failure()
        }
    }
}