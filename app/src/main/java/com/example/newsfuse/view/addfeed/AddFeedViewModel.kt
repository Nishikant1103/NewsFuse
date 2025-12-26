package com.example.newsfuse.view.addfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfuse.datasource.repository.AddFeedRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddFeedViewModel(private val repository: AddFeedRepository) : ViewModel() {

    private val _networkCallState = MutableStateFlow<FeedCheckState>(FeedCheckState.Idle)
    val networkCallState: StateFlow<FeedCheckState> = _networkCallState

    fun isValidNewsFeed(url: String) {
        viewModelScope.launch {
            _networkCallState.value = FeedCheckState.Loading
            val result = repository.verifyNewsFeedLink(url)
            _networkCallState.value = if(result) {
                FeedCheckState.Success
            } else {
                FeedCheckState.Failure
            }
        }
    }

    fun addFeedToLocalStorage(textName: String, textUrl: String) {
        viewModelScope.launch {
            repository.addFeedToLocalStorage(textName, textUrl)
        }
    }
}

sealed class FeedCheckState {
    object Idle : FeedCheckState()
    object Loading : FeedCheckState()
    object Success : FeedCheckState()
    object Failure : FeedCheckState()
}