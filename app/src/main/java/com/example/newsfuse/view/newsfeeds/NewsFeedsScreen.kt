package com.example.newsfuse.view.newsfeeds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.akhbaar.ui.theme.LocalAppDimensions
import com.example.newsfuse.R
import com.example.newsfuse.datasource.data.NewsFeed
import com.example.newsfuse.view.components.SwipeToDismiss

@Composable
fun NewsFeedsScreen(
    paddingValues: PaddingValues,
    floatingActionClicked: () -> Unit
) {
    // Implementation of the NewsFeedsScreen composable
    val feedsList = listOf<NewsFeed>(
        NewsFeed("Feed 1", "https://feed1.com"),
        NewsFeed("Feed 2", "https://feed2.com"),
        NewsFeed("Feed 3", "https://feed3.com"),
    )

    val scrollState = rememberLazyListState()

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        LazyColumn(state = scrollState, modifier = Modifier.fillMaxSize()) {
            itemsIndexed(
                feedsList.toList(),
                key = { _, feeds -> feeds.hashCode() }) { _, feed ->
                SwipeToDismiss(
                    item = feed,
                    itemComposable = { FeedsItem(feed) },
                    onEndToStartSwiped = {

                    },
                    onStartToEndSwiped = {

                    },
                    leftComposable = {
                        LeftActionItem()
                    },
                    rightComposable = {
                        RightActionItem()
                    },
                )
            }
        }


        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(LocalAppDimensions.dimenBig),
            onClick = {
                floatingActionClicked()
            },
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add Feed",
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}


@Composable
fun LeftActionItem() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .wrapContentSize(Alignment.CenterStart)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_checkbox),
            contentDescription = "Select Feed",
            tint = MaterialTheme.colorScheme.onTertiaryContainer,
            modifier = Modifier.padding(LocalAppDimensions.dimenMedium)
        )
    }
}

@Composable
fun RightActionItem() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.errorContainer)
            .wrapContentSize(Alignment.CenterEnd)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = "Remove Feed",
            tint = MaterialTheme.colorScheme.onErrorContainer,
            modifier = Modifier.padding(LocalAppDimensions.dimenLarge)
        )
    }
}

@Composable
fun FeedsItem(feed: NewsFeed) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Text(
            text = feed.feedName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalAppDimensions.dimenLarge),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = feed.feedUrl,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalAppDimensions.dimenLarge),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}