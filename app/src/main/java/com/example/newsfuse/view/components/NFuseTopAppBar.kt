package com.example.newsfuse.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import com.example.newsfuse.R
import com.example.newsfuse.core.ui.theme.LocalAppDimensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NFuseTopAppBar(
    titleText: String,
    scrollBehavior: TopAppBarScrollBehavior,
    showBackButton: Boolean,
    newsFeedClicked: () -> Unit,
    navigateUp: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            scrolledContainerColor = MaterialTheme.colorScheme.primaryContainer, // prevents color change
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Text(
                text = titleText,
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        scrollBehavior = scrollBehavior,
        actions = {
            IconButton(
                onClick = {
                    newsFeedClicked()
                },
                modifier = Modifier.size(LocalAppDimensions.dimen64)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_rss_feeds_logo),
                        contentDescription = "Feeds",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text("Feeds", style = MaterialTheme.typography.labelSmall)
                }
            }
        },
        navigationIcon = {
            if (!showBackButton) null else {
                IconButton(onClick = {
                    navigateUp()
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