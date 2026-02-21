package com.example.newsfuse.datasource.remote

import android.util.Log
import com.example.newsfuse.datasource.data.News
import com.example.newsfuse.datasource.data.RemoteNews
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser
import org.jsoup.select.Elements
import java.net.URL

/**
 * Data source for fetching news articles from a remote RSS feed URL.
 *
 * This class is responsible for parsing RSS feeds using Jsoup and converting them into a set of [News] objects.
 */
class NewsDataSource {

    /**
     * Fetches news articles from the given RSS feed URL.
     *
     * @param rssFeedUrl The URL of the RSS feed to fetch news from.
     * @return [Result.success] with a set of [News] if successful, or [Result.failure] with an exception otherwise.
     */
    fun getNewsFromFeedUrl(rssFeedUrl: String): Result<Set<RemoteNews>> {
        val newsSet = mutableSetOf<RemoteNews>()
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

    /**
     * Parses an RSS feed item [Element] and converts it to a [News] object.
     *
     * @param element The Jsoup [Element] representing a single RSS feed item.
     * @return A [News] object containing the parsed data.
     */
    private fun getNews(element: Element): RemoteNews {
        return RemoteNews(
            datePosted = element.allElements.select("pubDate").text(),
            newsTitle = element.allElements.select("title").text(),
            newsDescription = element.allElements.select("description").text(),
            newsLink = element.allElements.select("link").text(),
            newsImageLink = element.allElements.select("media\\:content").attr("url"),
        )
    }
}
