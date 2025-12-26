package com.example.newsfuse.view.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsfuse.view.addfeed.AddFeedScreen
import com.example.newsfuse.view.newsdetail.NewsDetailScreen
import com.example.newsfuse.view.newsfeeds.NewsFeedsScreen
import com.example.newsfuse.view.newslist.NewsListScreen
import kotlinx.serialization.Serializable

@Composable
fun NFuseNavHost(navController: NavHostController, innerPadding: PaddingValues,
                 floatingActionClicked: () -> Unit) {
    NavHost(
        navController = navController,
        startDestination = Routes.NewsList,
    ) {
        composable<Routes.NewsList> {
            NewsListScreen(
                paddingValues = innerPadding,
                onNewsItemClicked = { newsId ->
                    navController.navigate(Routes.NewsDetail(newsId))
                }
            )
        }

        composable<Routes.NewsDetail> { backStackEntry ->
            val newsId =
                backStackEntry.arguments?.getString("newsId") ?: ""
            if (!newsId.isEmpty()) {
                NewsDetailScreen(
                    newsId,
                    paddingValues = innerPadding
                )
            }
        }

        composable<Routes.NewsFeeds> {
            NewsFeedsScreen(
                paddingValues = innerPadding,
                floatingActionClicked = floatingActionClicked
            )
        }

        composable<Routes.AddFeed> {
            AddFeedScreen(paddingValues = innerPadding, feedAdded = {
                navController.navigateUp()
            })
        }
    }
}

sealed class Routes {
    @Serializable
    object NewsList : Routes()

    @Serializable
    data class NewsDetail(val newsId: String) : Routes()

    @Serializable
    object NewsFeeds : Routes()

    @Serializable
    object AddFeed : Routes()
}