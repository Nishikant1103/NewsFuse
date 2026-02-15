package com.example.newsfuse.view.newsfeeds

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.newsfuse.R
import com.example.newsfuse.core.Injector
import com.example.newsfuse.core.ui.theme.LocalAppDimensions
import com.example.newsfuse.datasource.data.NewsFeed
import com.example.newsfuse.view.components.SwipeToDismiss

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsFeedsScreen(
    paddingValues: PaddingValues,
    floatingActionClicked: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val viewModel = remember { Injector.getNewsFeedsViewModel(context) }
    val feedsList = viewModel.getNewsFeedList.collectAsStateWithLifecycle()
    var showDeleteFeedDialog by remember { mutableStateOf(false) }
    var actionableFeedId by remember { mutableIntStateOf(-1) }

    NewsFeedsScreenContent(
        feedsList = feedsList.value,
        paddingValues = paddingValues,
        showDeleteFeedDialog = showDeleteFeedDialog,
        actionableFeedId = actionableFeedId,
        onDeleteFeed = { feedId ->
            viewModel.deleteNewsFeed(feedId)
            showDeleteFeedDialog = false
        },
        onDismissDeleteDialog = { showDeleteFeedDialog = false },
        onFeedSelect = { feedId -> viewModel.updateFeedSelection(feedId) },
        onFeedDeleteSwipe = { feedId ->
            showDeleteFeedDialog = true
            actionableFeedId = feedId
        },
        floatingActionClicked = floatingActionClicked
    )
}

@Composable
private fun NewsFeedsScreenContent(
    feedsList: List<NewsFeed>,
    paddingValues: PaddingValues,
    showDeleteFeedDialog: Boolean,
    actionableFeedId: Int,
    onDeleteFeed: (Int) -> Unit,
    onDismissDeleteDialog: () -> Unit,
    onFeedSelect: (Int) -> Unit,
    onFeedDeleteSwipe: (Int) -> Unit,
    floatingActionClicked: () -> Unit
) {
    val scrollState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        if (feedsList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.feed_screen_empty_state_text),
                    modifier = Modifier.padding(LocalAppDimensions.dimen8),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            FeedsList(
                feedsList = feedsList,
                scrollState = scrollState,
                onFeedSelect = onFeedSelect,
                onFeedDeleteSwipe = onFeedDeleteSwipe
            )
        }

        FeedsFloatingActionButton(onClick = floatingActionClicked)
    }

    if (showDeleteFeedDialog) {
        DeleteFeedDialog(
            onDelete = { onDeleteFeed(actionableFeedId) },
            onDismiss = onDismissDeleteDialog,
            paddingValues = paddingValues
        )
    }
}

@Composable
private fun FeedsList(
    feedsList: List<NewsFeed>,
    scrollState: androidx.compose.foundation.lazy.LazyListState,
    onFeedSelect: (Int) -> Unit,
    onFeedDeleteSwipe: (Int) -> Unit
) {
    LazyColumn(state = scrollState, modifier = Modifier.fillMaxSize()) {
        itemsIndexed(
            feedsList,
            key = { _, feeds -> feeds.hashCode() }) { _, feed ->
            SwipeToDismiss(
                item = feed,
                itemComposable = { FeedsItem(feed) },
                onEndToStartSwiped = { onFeedDeleteSwipe(feed.id) },
                onStartToEndSwiped = { onFeedSelect(feed.id) },
                leftComposable = { LeftActionItem() },
                rightComposable = { RightActionItem() },
            )
        }
    }
}

@Composable
private fun FeedsFloatingActionButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            modifier = Modifier
                .padding(LocalAppDimensions.dimen32),
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = "Add Feed",
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeleteFeedDialog(
    onDelete: () -> Unit,
    onDismiss: () -> Unit,
    paddingValues: PaddingValues
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.padding(paddingValues)
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(LocalAppDimensions.dimen16)) {
                Text(
                    text = stringResource(R.string.delete_feed_dialog_text),
                )
                Spacer(modifier = Modifier.height(LocalAppDimensions.dimen32))
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = onDelete,
                    ) {
                        Text(stringResource(R.string.delete_feed_positive_button_text))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onDismiss,
                    ) {
                        Text(stringResource(R.string.delete_feed_negative_button_text))
                    }

                }
            }
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
            modifier = Modifier.padding(LocalAppDimensions.dimen8)
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
            modifier = Modifier.padding(LocalAppDimensions.dimen16)
        )
    }
}

@Composable
fun FeedsItem(feed: NewsFeed) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        border = if (feed.selected) {
            BorderStroke(1.dp, MaterialTheme.colorScheme.secondary)
        } else null,
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        val textColor = if (feed.selected) {
            MaterialTheme.colorScheme.onTertiaryContainer
        } else {
            MaterialTheme.colorScheme.onSurface
        }
        Text(
            text = feed.feedName,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalAppDimensions.dimen16),
            textAlign = TextAlign.Start,
            color = textColor,
        )
        Text(
            text = feed.feedUrl,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalAppDimensions.dimen16),
            textAlign = TextAlign.Start,
            color = textColor
        )
    }
}