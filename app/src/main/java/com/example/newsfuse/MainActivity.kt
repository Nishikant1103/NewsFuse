package com.example.newsfuse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsfuse.core.ui.theme.NewsFuseTheme
import com.example.newsfuse.core.utility.NetworkStatusLiveData
import com.example.newsfuse.view.components.EmptyState
import com.example.newsfuse.view.components.NFuseNavHost
import com.example.newsfuse.view.components.NFuseTopAppBar
import com.example.newsfuse.view.components.Routes
import com.example.newsfuse.view.newslist.NewsListScreen

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkStatusLiveData = NetworkStatusLiveData.getInstance(applicationContext)
        enableEdgeToEdge()
        setContent {
            NewsFuseTheme {
                val scrollBehavior =
                    TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = backStackEntry?.destination

                val showBackButton =
                    navController.previousBackStackEntry != null &&
                            currentDestination?.route != Routes.NewsList::class.simpleName
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = {
                            NFuseTopAppBar(
                                scrollBehavior,
                                showBackButton,
                                newsFeedClicked = {
                                    navController.navigate(Routes.NewsFeeds) {
                                        launchSingleTop = true
                                    }
                                },
                                navigateUp = {
                                    navController.navigateUp()
                                })
                        }
                    ) { innerPadding ->
                        val networkStatusLiveDataState =
                            networkStatusLiveData.observeAsState(initial = false)
                        if (networkStatusLiveDataState.value) {
                            NFuseNavHost(navController, innerPadding, floatingActionClicked = {
                                navController.navigate(Routes.AddFeed)
                            })
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


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsFuseTheme {
        NewsListScreen(paddingValues = PaddingValues(4.dp))
    }
}