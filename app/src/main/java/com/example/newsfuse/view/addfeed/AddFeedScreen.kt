package com.example.newsfuse.view.addfeed

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.akhbaar.ui.theme.LocalAppDimensions
import com.example.newsfuse.R
import com.example.newsfuse.core.Injector


@Composable
fun AddFeedScreen(paddingValues: PaddingValues, feedAdded: () -> Unit) {
    val brushColorList = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
    )
    val context = androidx.compose.ui.platform.LocalContext.current
    val viewModel = remember { Injector.getAddFeedViewModel(context) }
    Column(modifier = Modifier.padding(paddingValues)) {
        val brush = remember {
            Brush.linearGradient(
                colors = brushColorList
            )
        }
        var textName by remember { mutableStateOf("") }
        var textUrl by remember { mutableStateOf("") }
        val isValidUrl = remember(textUrl) {
            Patterns.WEB_URL.matcher(textUrl).matches()
        }
        val isNetworkCallSuccess by viewModel.networkCallState.collectAsState()

        if (isNetworkCallSuccess is FeedCheckState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)) // semi-transparent overlay
                    .clickable(enabled = false) {} // blocks clicks behind
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        }

        val successToastMessage = stringResource(R.string.add_feed_success_toast)
        val failureToastMessage = stringResource(R.string.add_feed_failure_toast)

        LaunchedEffect(isNetworkCallSuccess) {
            when(isNetworkCallSuccess) {
                FeedCheckState.Failure -> {
                    Toast.makeText(context, failureToastMessage, Toast.LENGTH_SHORT).show()
                }
                FeedCheckState.Success -> {
                    viewModel.addFeedToLocalStorage(textName, textUrl)
                    Toast.makeText(context, successToastMessage, Toast.LENGTH_SHORT).show()
                    feedAdded()
                }
                else -> Unit
            }
        }


        Text(
            stringResource(R.string.add_feed_help_text),
            modifier = Modifier.padding(
                LocalAppDimensions.dimenMedium
            )
        )
        OutlinedTextField(
            value = textName,
            onValueChange = { textName = it },
            label = { Text(stringResource(R.string.add_feed_name_text_field_label)) },
            textStyle = TextStyle(brush = brush),
            modifier = Modifier.padding(
                LocalAppDimensions.dimenMedium
            ),
        )

        OutlinedTextField(
            value = textUrl,
            onValueChange = { textUrl = it },
            label = { Text(stringResource(R.string.add_feed_url_text_field_label)) },
            textStyle = TextStyle(brush = brush),
            modifier = Modifier.padding(
                LocalAppDimensions.dimenMedium
            ),
            isError = textUrl.isNotEmpty() && !isValidUrl,
            supportingText = {
                if (textUrl.isNotEmpty() && !isValidUrl) {
                    Text(stringResource(R.string.add_feed_url_text_field_supporting_text))
                }
            }
        )

        ElevatedButton(
            modifier = Modifier.padding(LocalAppDimensions.dimenMedium),
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContentColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.50f),
                disabledContainerColor = MaterialTheme.colorScheme.onPrimary
            ),
            enabled = textName.isNotEmpty() && isValidUrl,
            onClick = {
                viewModel.isValidNewsFeed(textUrl)
            }) {
            Text(stringResource(R.string.add_feed_button))
        }
    }
}