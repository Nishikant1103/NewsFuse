package com.example.newsfuse.datasource.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewsEntity(
    @PrimaryKey
    val id: String,
    val datePosted: String = "",
    val newsTitle: String = "",
    val newsDescription: String = "",
    val newsLink: String = "",
    val newsImage: String = "",
)
