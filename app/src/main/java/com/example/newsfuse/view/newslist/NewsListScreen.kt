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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
    // Get context to pass to Injector
    val context = androidx.compose.ui.platform.LocalContext.current
    val viewModel = remember { Injector.getNewsListViewModel(context) }
    val newsListState = viewModel.getNewsListLD.observeAsState()
    val newsList = newsListState.value ?: emptySet()
    val scrollState = rememberLazyListState()
    val selectedFeed by viewModel.getSelectedFeed.observeAsState()

    if (newsList.isEmpty()) {
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

    LazyColumn(state = scrollState, modifier = Modifier.padding(paddingValues)) {
        itemsIndexed(newsList.toList(), key = { _, news -> news.hashCode() }) { _, news ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(
                        horizontal = LocalAppDimensions.dimen16,
                        vertical = LocalAppDimensions.dimen8
                    )
                    .clickable(
                        onClick = {
                            onNewsItemClicked(news.id)
                        }
                    ),
                colors = cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(
                    text = news.newsTitle,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LocalAppDimensions.dimen16),
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
                Text(
                    text = (selectedFeed?.feedName ?: "") + " | " + news.datePosted,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LocalAppDimensions.dimen16),
                    textAlign = TextAlign.Right,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }
        }
    }
}