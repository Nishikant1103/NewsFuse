package com.example.newsfuse.view.newsdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfuse.datasource.data.News
import com.example.newsfuse.datasource.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsDetailViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _getNewsDetail = MutableLiveData<News>()
    val getNewsDetailLD: MutableLiveData<News> = _getNewsDetail

    fun fetchNewsDetail(newsId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNewsById(newsId)
            news.let {
                _getNewsDetail.postValue(news)
            }
        }
    }
}