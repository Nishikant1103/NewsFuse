package com.example.newsfuse.core

import com.example.newsfuse.datasource.remote.NewsDataSource
import com.example.newsfuse.datasource.repository.NewsRepository
import com.example.newsfuse.view.newslist.NewsListViewModel

class Injector {
    companion object {
        val newsDataSource: NewsDataSource by lazy {
            NewsDataSource()
        }
        val newsRepository: NewsRepository by lazy {
            NewsRepository(newsDataSource)
        }
        val newsListViewModel: NewsListViewModel by lazy {
            NewsListViewModel(newsRepository)
        }
    }
}