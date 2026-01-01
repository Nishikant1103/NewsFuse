package com.example.newsfuse.datasource.data

import com.example.newsfuse.core.utility.TimeFormatter
import com.example.newsfuse.datasource.local.db.entity.NewsEntity
import java.util.UUID


data class News(
    val id: String = UUID.randomUUID().toString(),
    val datePosted: String,
    val newsTitle: String,
    val newsDescription: String,
    val newsLink: String,
    val newsImageLink: String,
)

fun toNews(entity: NewsEntity): News = News(
    id = entity.id,
    datePosted = TimeFormatter().getFormattedTime(
        inputFormat = listOf(
            "EEE, dd MMM yyyy HH:mm:ss z",   // GMT / PST / CET …
            "EEE, dd MMM yyyy HH:mm:ss Z"    // +0000 / -0530 …
        ),
        outputFormat = "dd.MM.yyyy HH:mm",
        entity.datePosted
    ) ?: entity.datePosted,
    newsTitle = entity.newsTitle,
    newsDescription = entity.newsDescription,
    newsLink = entity.newsLink,
    newsImageLink = entity.newsImage
)