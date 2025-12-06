package com.example.newsfuse.datasource.repository

import com.example.newsfuse.core.Injector
import com.example.newsfuse.datasource.data.News
import com.example.newsfuse.datasource.remote.NewsDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepository(private val newsDataSource: NewsDataSource = Injector.newsDataSource) {
    companion object {
        private const val SAMPLE_URL =
            "https://www.thehindu.com/feeder/default.rss" //TODO: Remove this hard coded URL
        private const val REFRESH_DELAY = 100000L
    }

    val getLatestNews: Flow<Result<Set<News>>> = flow {
        while (true) {
            val latestNews = newsDataSource.getNewsFromFeedUrl(SAMPLE_URL)
            emit(latestNews)
            delay(REFRESH_DELAY)

        }
    }
}