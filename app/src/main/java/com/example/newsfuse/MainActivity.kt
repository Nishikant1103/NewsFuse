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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.newsfuse.core.ui.theme.NewsFuseTheme
import com.example.newsfuse.core.utility.NetworkStatusLiveData
import com.example.newsfuse.view.components.NFuseNavHost
import com.example.newsfuse.view.components.NFuseTopAppBar
import com.example.newsfuse.view.components.Routes
import com.example.newsfuse.view.newslist.NewsListScreen
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkStatusLiveData = NetworkStatusLiveData.getInstance(applicationContext)
        enableEdgeToEdge()
        setContent {
            NewsFuseTheme {
                val topAppBarState = rememberTopAppBarState()
                val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topAppBarState)
                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = backStackEntry?.destination
                val showBackButton = navController.previousBackStackEntry != null &&
                        currentDestination?.route != Routes.NewsList::class.simpleName
                val snackbarHostState = remember { SnackbarHostState() }
                val coroutineScope = rememberCoroutineScope()
                val lastNetworkStatusState = remember { mutableStateOf(true) }
                val networkStatusLiveDataState = networkStatusLiveData.observeAsState(initial = true)

                val internetRestoredMsg = stringResource(R.string.internet_connection_restored)
                val noInternetMsg = stringResource(R.string.no_internet_connection)
                // Show snackbar on network status change
                LaunchedEffect(networkStatusLiveDataState.value) {
                    if (lastNetworkStatusState.value != networkStatusLiveDataState.value) {
                        val message = if (networkStatusLiveDataState.value) {
                            internetRestoredMsg
                        } else {
                            noInternetMsg
                        }
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                        lastNetworkStatusState.value = networkStatusLiveDataState.value
                    }
                }

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
                                titleText = stringResource(R.string.app_name),
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
                        },
                        snackbarHost = { SnackbarHost(snackbarHostState) }
                    ) { innerPadding ->
                        // Always show main content, even if offline
                        NFuseNavHost(
                            navController,
                            innerPadding,
                            floatingActionClicked = {
                                navController.navigate(Routes.AddFeed)
                            })
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