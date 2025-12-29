package com.example.newsfuse.view.newsdetail


import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.newsfuse.core.ui.theme.LocalAppDimensions
import com.example.newsfuse.R
import com.example.newsfuse.core.Injector
import com.example.newsfuse.core.ui.theme.NewsFuseTheme
import com.example.newsfuse.datasource.data.News
import coil.compose.AsyncImage


@Composable
fun NewsDetailScreen(newsId: String, paddingValues: PaddingValues) {
    // Get context to pass to Injector
    val context = LocalContext.current
    val viewModel = remember { Injector.getNewsDetailViewModel(context) }

    LaunchedEffect(newsId) {
        viewModel.fetchNewsDetail(newsId)
    }

    val newsDetail = viewModel.getNewsDetailLD.observeAsState()

    Card(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(LocalAppDimensions.dimen16),
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        val context = LocalContext.current
        Text(
            text = newsDetail.value?.newsTitle ?: "",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalAppDimensions.dimen16),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )

        Text(
            text = newsDetail.value?.newsDescription ?: "",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalAppDimensions.dimen16),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )

        AsyncImage(
            model = newsDetail.value?.newsImageLink,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = LocalAppDimensions.dimen16,
                    horizontal = LocalAppDimensions.dimen4
                ), alignment = Alignment.Center
        )

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
            Icon(
                painter = painterResource(id = R.drawable.open_news_article_in_browser),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .size(LocalAppDimensions.dimen32 + LocalAppDimensions.dimen16)
                    .align(Alignment.CenterHorizontally)
                    .clickable(
                        onClick = {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                newsDetail.value?.newsLink?.toUri()
                            )
                            context.startActivity(intent)
                        }
                    )
            )
            Text(
                text = stringResource(R.string.go_to_article),
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LocalAppDimensions.dimen2),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = newsDetail.value?.datePosted ?: "",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LocalAppDimensions.dimen16),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

    }
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun NewsDetailedViewPreview() {
    val dummyNews = News(
        newsTitle = "News 1",
        newsDescription = "News 1 description",
        newsLink = "abc@gmail.com"
    )

    NewsFuseTheme {
        NewsDetailScreen("", paddingValues = PaddingValues(4.dp))
    }
}