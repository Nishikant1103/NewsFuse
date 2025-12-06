package com.example.newsfuse.view.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfuse.core.Injector
import com.example.newsfuse.datasource.data.News
import com.example.newsfuse.datasource.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsListViewModel(private val repository: NewsRepository = Injector.newsRepository) : ViewModel() {
    private val _getLatestNews = MutableLiveData<Result<Set<News>>>().apply {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getLatestNews.collect {
                postValue(it)
            }
        }
    }


    val getNewsListLD: LiveData<Result<Set<News>>> = _getLatestNews
}