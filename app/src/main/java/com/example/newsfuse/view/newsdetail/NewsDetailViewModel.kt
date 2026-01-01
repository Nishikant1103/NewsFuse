package com.example.newsfuse.view.newsdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfuse.datasource.data.News
import com.example.newsfuse.datasource.data.toNews
import com.example.newsfuse.datasource.repository.NewsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class NewsDetailViewModel(private val repository: NewsRepository) : ViewModel() {
    fun newDetail(newsId: String): StateFlow<News?> =
        repository.getNewsById(newsId).map {
            it?.let { toNews(it) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )
}