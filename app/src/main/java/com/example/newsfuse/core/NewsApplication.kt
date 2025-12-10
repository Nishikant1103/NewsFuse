package com.example.newsfuse.core

import android.app.Application
import android.util.Log
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.newsfuse.datasource.local.db.NewsDatabase
import com.example.newsfuse.workers.NewsProviderWorker
import java.util.concurrent.TimeUnit

class NewsApplication : Application() {

    companion object {
        const val RSS_FEED_URL_KEY = "RSS_FEED_URL"
        const val NEWS_SYNC_WORK_TAG = "news_sync_work_tag"
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

    private fun startPeriodicNewsSync() {
        val inputData = Data.Builder()
            .putString(RSS_FEED_URL_KEY, "https://www.thehindu.com/feeder/default.rss")
            .build()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<NewsProviderWorker>(
            15, TimeUnit.MINUTES // Minimum interval
        )
            .setInputData(inputData)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            NEWS_SYNC_WORK_TAG,
            ExistingPeriodicWorkPolicy.KEEP, // Don't replace if already running
            periodicWorkRequest
        )
    }
}

