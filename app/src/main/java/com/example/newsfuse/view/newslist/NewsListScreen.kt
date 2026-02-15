package com.example.newsfuse.view.newslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.newsfuse.R
import com.example.newsfuse.core.Injector
import com.example.newsfuse.core.ui.theme.LocalAppDimensions

@Composable
fun NewsListScreen(
    paddingValues: PaddingValues,
    onNewsItemClicked: (newsId: String) -> Unit = {}
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val viewModel = remember { Injector.getNewsListViewModel(context) }
    val newsList by viewModel.getLatestNewsSet.collectAsState()
    val scrollState = rememberLazyListState()
    val selectedFeed by viewModel.getSelectedFeed.collectAsState()

    NewsListScreenContent(
        newsList = newsList,
        selectedFeedName = selectedFeed?.feedName,
        paddingValues = paddingValues,
        scrollState = scrollState,
        onNewsItemClicked = onNewsItemClicked
    )
}

@Composable
private fun NewsListScreenContent(
    newsList: Set<com.example.newsfuse.datasource.data.News>,
    selectedFeedName: String?,
    paddingValues: PaddingValues,
    scrollState: androidx.compose.foundation.lazy.LazyListState,
    onNewsItemClicked: (newsId: String) -> Unit
) {
    if (newsList.isEmpty()) {
        EmptyNewsListState(paddingValues)
    } else {
        NewsListLazyColumn(
            newsList = newsList,
            selectedFeedName = selectedFeedName,
            paddingValues = paddingValues,
            scrollState = scrollState,
            onNewsItemClicked = onNewsItemClicked
        )
    }
}

@Composable
private fun EmptyNewsListState(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = stringResource(R.string.no_selected_feed_empty_state_text),
            modifier = Modifier.padding(LocalAppDimensions.dimen8)
        )
    }
}

@Composable
private fun NewsListLazyColumn(
    newsList: Set<com.example.newsfuse.datasource.data.News>,
    selectedFeedName: String?,
    paddingValues: PaddingValues,
    scrollState: androidx.compose.foundation.lazy.LazyListState,
    onNewsItemClicked: (newsId: String) -> Unit
) {
    LazyColumn(state = scrollState, modifier = Modifier.padding(paddingValues)) {
        itemsIndexed(newsList.toList(), key = { _, news -> news.hashCode() }) { _, news ->
            NewsListItem(
                news = news,
                selectedFeedName = selectedFeedName,
                onNewsItemClicked = onNewsItemClicked
            )
        }
    }
}

@Composable
private fun NewsListItem(
    news: com.example.newsfuse.datasource.data.News,
    selectedFeedName: String?,
    onNewsItemClicked: (newsId: String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                horizontal = LocalAppDimensions.dimen16,
                vertical = LocalAppDimensions.dimen8
            )
            .clickable { onNewsItemClicked(news.id) },
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(
            text = news.newsTitle,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalAppDimensions.dimen16),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = (selectedFeedName ?: "") + " | " + news.datePosted,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalAppDimensions.dimen16),
            textAlign = TextAlign.Right,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}