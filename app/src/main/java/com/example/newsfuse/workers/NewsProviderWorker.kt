package com.example.newsfuse.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.newsfuse.datasource.remote.NewsDataSource


class NewsProviderWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val newsDataSource: NewsDataSource
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        return Result.success()
    }
}