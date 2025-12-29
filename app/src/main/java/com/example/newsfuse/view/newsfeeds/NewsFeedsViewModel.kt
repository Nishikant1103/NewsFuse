package com.example.newsfuse.view.newsfeeds

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfuse.datasource.data.NewsFeed
import com.example.newsfuse.datasource.repository.NewsFeedsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsFeedsViewModel(private val newsFeedsRepository: NewsFeedsRepository) : ViewModel() {
    private val _getNewsFeedsList = MutableStateFlow<List<NewsFeed>>(
        value = emptyList()
    ).apply {
        viewModelScope.launch {
            newsFeedsRepository.getAllFeeds.collect { list ->
                value = list
            }
        }
    }

    val getNewsFeedList: StateFlow<List<NewsFeed>> = _getNewsFeedsList

    fun updateFeedSelection(feedId: Int) {
        try {
            viewModelScope.launch {
                newsFeedsRepository.updateFeedSelection(feedId)
                newsFeedsRepository.startImmediateSync()
            }
        } catch (e: Exception) {
            Log.e("NewsFeedsViewModel::class", "Exception: $e")
        }
    }

    fun deleteNewsFeed(feedId: Int) {
        viewModelScope.launch {
            newsFeedsRepository.deleteFeed(feedId)
        }
    }
}