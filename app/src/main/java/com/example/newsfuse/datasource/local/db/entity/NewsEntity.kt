package com.example.newsfuse.datasource.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsfuse.core.utility.TimeFormatter
import com.example.newsfuse.datasource.data.News

@Entity
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val datePosted: String = "",
    val newsTitle: String = "",
    val newsDescription: String = "",
    val newsLink: String = "",
    val newsImage: String = "",
)


fun NewsEntity.toNews(): News =
    News(
        id = id,
        datePosted = TimeFormatter.getFormattedTime(
            inputFormat = listOf(
                "EEE, dd MMM yyyy HH:mm:ss z",
                "EEE, dd MMM yyyy HH:mm:ss Z"
            ),
            outputFormat = "dd.MM.yyyy HH:mm",
            datePosted
        ) ?: datePosted,
        newsTitle = newsTitle,
        newsDescription = newsDescription,
        newsLink = newsLink,
        newsImageLink = newsImage
    )
