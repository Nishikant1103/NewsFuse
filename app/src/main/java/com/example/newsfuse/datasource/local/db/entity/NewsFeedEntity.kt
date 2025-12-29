package com.example.newsfuse.datasource.local.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = ["feedUrl"], unique = true)
    ]
)

data class NewsFeedEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val feedName: String,
    val feedUrl: String,
    val selected: Boolean = false
)