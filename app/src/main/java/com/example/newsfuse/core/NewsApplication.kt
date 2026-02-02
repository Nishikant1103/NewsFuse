package com.example.newsfuse.core

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.newsfuse.datasource.local.db.NewsDatabase
import com.example.newsfuse.workers.NewsProviderWorker
import java.util.concurrent.TimeUnit

/**
 * Application class for the NewsFuse app.
 *
 * Initializes the singleton instance of [NewsDatabase] and sets up a periodic background
 * sync using WorkManager to fetch news updates.
 */
class NewsApplication : Application() {
    companion object {
        const val NEWS_SYNC_WORK_TAG = "news_sync_work_tag"
        const val IMMEDIATE_NEWS_SYNC = "immediate_news_sync"
    }

    // NewsDatabase singleton instance
    val database by lazy { NewsDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()

        // Initialize NewsDatabase singleton
        database

        // Start periodic news sync
        startPeriodicNewsSync()
    }

    /**
     * Schedules a periodic background sync using WorkManager to fetch news updates.
     *
     * Sets network and battery constraints, and enqueues a unique periodic work request
     * for the [NewsProviderWorker] to run every 15 minutes.
     */
    private fun startPeriodicNewsSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<NewsProviderWorker>(
            15, TimeUnit.MINUTES // Minimum interval
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            NEWS_SYNC_WORK_TAG,
            ExistingPeriodicWorkPolicy.KEEP, // Don't replace if already running
            periodicWorkRequest
        )
    }
}

