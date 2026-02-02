package com.example.newsfuse.datasource.repository

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.newsfuse.core.NewsApplication.Companion.IMMEDIATE_NEWS_SYNC
import com.example.newsfuse.datasource.data.NewsFeed
import com.example.newsfuse.datasource.local.db.dao.FeedsDao
import com.example.newsfuse.datasource.local.db.dao.NewsDao
import com.example.newsfuse.datasource.local.db.entity.NewsFeedEntity
import com.example.newsfuse.datasource.remote.NewsDataSource
import com.example.newsfuse.workers.NewsProviderWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Repository for managing news feeds and related operations.
 *
 * This class provides methods to verify, add, update, and delete news feeds, as well as to trigger
 * immediate news synchronization using WorkManager. It also exposes a Flow of all news feeds for UI observation.
 *
 * @property newsDataSource The remote data source for fetching news articles.
 * @property feedsDao DAO for accessing news feed entities in the local database.
 * @property newsDao DAO for accessing news articles in the local database.
 * @property context Application context, used for WorkManager operations.
 */
class NewsFeedsRepository(
    private val newsDataSource: NewsDataSource,
    private val feedsDao: FeedsDao,
    private val newsDao: NewsDao,
    private val context: Context
) {

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

    val getAllFeeds: Flow<List<NewsFeed>> = feedsDao.getAllFeedEntities()
        .map { feedEntityList ->
            feedEntityList.map { feedEntity ->
                toNewsFeed(feedEntity)
            }
        }

    private fun toNewsFeed(newsFeedEntity: NewsFeedEntity): NewsFeed {
        return NewsFeed(
            id = newsFeedEntity.id,
            feedName = newsFeedEntity.feedName,
            feedUrl = newsFeedEntity.feedUrl,
            selected = newsFeedEntity.selected
        )
    }

    suspend fun updateFeedSelection(feedId: Int) {
        feedsDao.updateSelection(feedId)
    }

    /**
     * Triggers an immediate, one-time news synchronization using WorkManager.
     *
     * This method is intended for use cases where the user selects a news source and wants to fetch
     * the latest news on demand, outside of the periodic sync schedule. It enqueues a unique work
     * request with network and battery constraints, ensuring only one such sync runs at a time.
     */
    fun startImmediateSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val immediateWorkRequest = OneTimeWorkRequestBuilder<NewsProviderWorker>()
            .setConstraints(constraints)
            .build()

        WorkManager
            .getInstance(context)
            .enqueueUniqueWork(
                uniqueWorkName = IMMEDIATE_NEWS_SYNC,
                existingWorkPolicy = ExistingWorkPolicy.REPLACE,
                request = immediateWorkRequest
            )
    }

    suspend fun deleteFeed(id: Int) {
        feedsDao.getFeedById(id).first().let {
            if (it?.selected ?: false) {
                newsDao.deleteAllNewsEntities()
            }
        }
        feedsDao.deleteFeedById(id)
    }
}