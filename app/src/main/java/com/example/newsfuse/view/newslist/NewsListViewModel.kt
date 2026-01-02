package com.example.newsfuse.view.newslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfuse.datasource.data.News
import com.example.newsfuse.datasource.data.NewsFeed
import com.example.newsfuse.datasource.data.toNewsFeed
import com.example.newsfuse.datasource.repository.NewsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Suppress("unused")
class NewsListViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    val getLatestNewsSet: StateFlow<Set<News>> =
        newsRepository.getLatestNews.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptySet()
        )


    val getSelectedFeed: StateFlow<NewsFeed?> = newsRepository.getSelectedFeed().map {
        it?.let { toNewsFeed(it) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )
}