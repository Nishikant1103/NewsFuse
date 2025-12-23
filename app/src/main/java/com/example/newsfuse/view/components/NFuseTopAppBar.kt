package com.example.newsfuse.view.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController

import com.example.newsfuse.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NFuseTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    navController: NavHostController,
    showBackButton: Boolean
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = "The Hindu",
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        scrollBehavior = scrollBehavior,
        actions = {
            // Navigate to Feeds screen
            IconButton(onClick = {
                navController.navigate(Routes.NewsFeeds)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_rss_feeds_logo),
                    contentDescription = "Feeds",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        navigationIcon = {
            if (!showBackButton) null else {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_button),
                        contentDescription = "Back button",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    )
}