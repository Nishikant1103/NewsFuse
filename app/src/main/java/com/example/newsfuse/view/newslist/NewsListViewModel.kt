package com.example.newsfuse.view.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfuse.datasource.data.News
import com.example.newsfuse.datasource.local.db.entity.NewsFeedEntity
import com.example.newsfuse.datasource.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NewsListViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _getLatestNews = MutableLiveData<Set<News>>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLatestNews.collect { newsSet ->
                postValue(newsSet)
            }
        }
    }

    val getNewsListLD: LiveData<Set<News>> = _getLatestNews

    private val _getSelectedFeed = MutableLiveData<NewsFeedEntity?>(
        null
    ).apply {
        viewModelScope.launch(Dispatchers.IO) {
            postValue(repository.getSelectedFeed().first())
        }
    }

    val getSelectedFeed: LiveData<NewsFeedEntity?> = _getSelectedFeed
}