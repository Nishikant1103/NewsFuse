package com.example.newsfuse.view.newslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import com.example.akhbaar.ui.theme.LocalAppDimensions

@Composable
fun NewsListScreen(
    viewModel: NewsListViewModel = NewsListViewModel(),
    paddingValues: PaddingValues
) {
    val newsListState = viewModel.getNewsListLD.observeAsState()

    if (newsListState.value?.isFailure == true) {
        // Show error UI
    } else {
        val newsList = newsListState.value?.getOrNull() ?: emptySet()
        // Show news list UI using newsList

        val scrollState = rememberLazyListState()

        LazyColumn(state = scrollState, modifier = Modifier.padding(paddingValues)) {
            itemsIndexed(newsList.toList(), key = { _, news -> news.hashCode() }) { _, news ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(
                            horizontal = LocalAppDimensions.dimenLarge,
                            vertical = LocalAppDimensions.dimenMedium
                        )
                        .clickable(
                            onClick = {
                                //TODO: Handle news item click
                            }
                        ),
                    colors = cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer
                    )
                ) {
                    Text(
                        text = news.newsTitle,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(LocalAppDimensions.dimenLarge),
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(
                        text = news.datePosted,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(LocalAppDimensions.dimenLarge),
                        textAlign = TextAlign.Right,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }
            }
        }
    }

}