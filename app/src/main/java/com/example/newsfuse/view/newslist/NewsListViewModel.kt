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

class NewsListViewModel(private val repository: NewsRepository) : ViewModel() {
    val getLatestNewsSet: StateFlow<Set<News>> =
        repository.getLatestNews.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )


    val getSelectedFeed: StateFlow<NewsFeed?> = repository.getSelectedFeed().map {
        it?.let { toNewsFeed(it) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )
}