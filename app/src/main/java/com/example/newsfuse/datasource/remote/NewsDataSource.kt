package com.example.newsfuse.datasource.remote

import android.util.Log
import com.example.newsfuse.datasource.data.News
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import org.jsoup.select.Elements
import java.net.URL

class NewsDataSource {

    fun getNewsFromFeedUrl(rssFeedUrl: String): Result<Set<News>> {
        val newsSet = mutableSetOf<News>()
        val rssUrl = URL(rssFeedUrl)
        try {
            val document: Document =
                Jsoup.parse(rssUrl.openStream(), "UTF-8", "", Parser.xmlParser())
            val itemElement: Elements = document.select("item")

            if (itemElement.isNotEmpty()) {
                for (item in itemElement) {
                    newsSet.add(getNews(item))
                }
                return Result.success(newsSet)
            } else {
                return Result.failure(Exception("No news items found"))
            }
        } catch (exception: Exception) {
            Log.e(
                "NewsDataSource::class",
                "Failed to fetch news due to $exception exception"
            )
            return Result.failure(Exception(exception))
        }
    }

    private fun getNews(element: Element): News {
        return News(
            datePosted = element.allElements.select("pubDate").text(),
            newsTitle = element.allElements.select("title").text(),
            newsDescription = element.allElements.select("description").text(),
            newsLink = element.allElements.select("link").text(),
            newsImageLink = element.allElements.select("media\\:content").attr("url"),
        )
    }
}
