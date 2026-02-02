package com.example.newsfuse.datasource.data


/**
 * Data class representing a news feed source.
 *
 * @property id Unique identifier for the news feed.
 * @property feedName The display name of the news feed.
 * @property feedUrl The URL of the news feed (RSS/Atom).
 * @property selected Whether this feed is currently selected by the user.
 */
data class NewsFeed(
    val id: Int,
    val feedName: String,
    val feedUrl: String,
    val selected: Boolean
)

