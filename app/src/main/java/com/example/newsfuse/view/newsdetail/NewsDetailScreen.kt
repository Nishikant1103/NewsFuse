package com.example.newsfuse.view.newsdetail


import android.content.Intent
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.newsfuse.R
import com.example.newsfuse.core.Injector
import com.example.newsfuse.core.ui.theme.LocalAppDimensions


@Composable
fun NewsDetailScreen(newsId: String, paddingValues: PaddingValues) {
    val context = LocalContext.current
    val viewModel = remember { Injector.getNewsDetailViewModel(context) }
    val newsDetail by viewModel.newDetail(newsId).collectAsState()

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
            text = newsDetail?.newsTitle ?: "",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalAppDimensions.dimen16),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )

        Text(
            text = newsDetail?.newsDescription ?: "",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalAppDimensions.dimen16),
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )

        AsyncImage(
            model = newsDetail?.newsImageLink,
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
                                newsDetail?.newsLink?.toUri()
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
                text = newsDetail?.datePosted ?: "",
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