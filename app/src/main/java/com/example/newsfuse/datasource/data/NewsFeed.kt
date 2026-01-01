package com.example.newsfuse.datasource.data

import com.example.newsfuse.datasource.local.db.entity.NewsFeedEntity

data class NewsFeed(
    val id: Int,
    val feedName: String,
    val feedUrl: String,
    val selected: Boolean
)

fun toNewsFeed(newsFeedEntity: NewsFeedEntity) = NewsFeed(
    id = newsFeedEntity.id,
    feedName = newsFeedEntity.feedName,
    feedUrl = newsFeedEntity.feedUrl,
    selected = newsFeedEntity.selected
)