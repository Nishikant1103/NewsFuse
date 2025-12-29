package com.example.newsfuse.datasource.data

data class NewsFeed(
    val id: Int,
    val feedName: String,
    val feedUrl: String,
    val selected: Boolean
)