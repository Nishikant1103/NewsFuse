package com.example.newsfuse.datasource.data

import java.util.UUID


data class News(
    val id: String = UUID.randomUUID().toString(),
    val datePosted: String?,
    val newsTitle: String,
    val newsDescription: String,
    val newsLink: String,
    val newsImageLink: String,
)