package com.example.newsfuse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.NewsFuseTheme
import com.example.newsfuse.core.utility.NetworkStatusLiveData
import com.example.newsfuse.view.components.EmptyState
import com.example.newsfuse.view.newsdetail.NewsDetailScreen
import com.example.newsfuse.view.newslist.NewsListScreen
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkStatusLiveData = NetworkStatusLiveData.getInstance(applicationContext)
        enableEdgeToEdge()
        setContent {
            NewsFuseTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        val networkStatusLiveDataState =
                            networkStatusLiveData.observeAsState(initial = false)
                        if (networkStatusLiveDataState.value) {
                            NavHost(
                                navController = navController,
                                startDestination = NewsList,
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                composable<NewsList> {
                                    NewsListScreen(
                                        paddingValues = innerPadding,
                                        onNewsItemClicked = { newsId ->
                                            navController.navigate(NewsDetail(newsId))
                                        }
                                    )
                                }

                                composable<NewsDetail> { backStackEntry ->
                                    val newsId =
                                        backStackEntry.arguments?.getString("newsId") ?: ""
                                    if (!newsId.isEmpty()) {
                                        NewsDetailScreen(
                                            newsId,
                                            paddingValues = innerPadding
                                        )
                                    }
                                }
                            }
                        } else {
                            EmptyState(
                                R.raw.no_internet,
                                "No Internet Connection",
                                "Please check your network settings and try again."
                            )
                        }
                    }
                }
            }
        }
    }
}

@Serializable
object NewsList

@Serializable
data class NewsDetail(val newsId: String)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsFuseTheme {
        NewsListScreen(paddingValues = PaddingValues(4.dp))
    }
}