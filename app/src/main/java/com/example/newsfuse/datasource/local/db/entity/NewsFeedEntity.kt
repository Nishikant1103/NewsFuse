package com.example.newsfuse.datasource.local.db.entity

import androidx.room.PrimaryKey


data class NewsFeedEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val feedName: String,
    val feedUrl: String,
)