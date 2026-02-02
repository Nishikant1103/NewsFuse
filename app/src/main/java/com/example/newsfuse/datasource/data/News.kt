package com.example.newsfuse.datasource.data

import java.util.UUID

/**
 * Data class representing a news article.
 *
 * @property id Unique identifier for the news article. Defaults to a random UUID.
 * @property datePosted The date when the news was posted, as a String (nullable).
 * @property newsTitle The title of the news article.
 * @property newsDescription The description or summary of the news article.
 * @property newsLink The URL link to the full news article.
 * @property newsImageLink The URL link to the image associated with the news article.
 */
data class News(
    val id: String = UUID.randomUUID().toString(),
    val datePosted: String?,
    val newsTitle: String,
    val newsDescription: String,
    val newsLink: String,
    val newsImageLink: String,
)