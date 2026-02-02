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
import com.example.newsfuse.core.ui.theme.LocalAppDimensions
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
    var textName by remember { mutableStateOf("") }
    var textUrl by remember { mutableStateOf("") }
    val isValidUrl = remember(textUrl) {
        Patterns.WEB_URL.matcher(textUrl).matches()
    }
    val isNetworkCallSuccess by viewModel.networkCallState.collectAsState()

    val brush = remember {
        Brush.linearGradient(colors = brushColorList)
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

    Column(modifier = Modifier.padding(paddingValues)) {
        AddFeedHelpText()
        AddFeedNameField(
            value = textName,
            onValueChange = { textName = it },
            brush = brush
        )
        AddFeedUrlField(
            value = textUrl,
            onValueChange = { textUrl = it },
            brush = brush,
            isValidUrl = isValidUrl
        )
        AddFeedButton(
            enabled = textName.isNotEmpty() && isValidUrl,
            onClick = { viewModel.isValidNewsFeed(textUrl) }
        )
        if (isNetworkCallSuccess is FeedCheckState.Loading) {
            LoadingOverlay()
        }
    }
}

@Composable
private fun AddFeedHelpText() {
    Text(
        stringResource(R.string.add_feed_help_text),
        modifier = Modifier.padding(LocalAppDimensions.dimen8)
    )
}

@Composable
private fun AddFeedNameField(value: String, onValueChange: (String) -> Unit, brush: Brush) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.add_feed_name_text_field_label)) },
        textStyle = TextStyle(brush = brush),
        modifier = Modifier.padding(LocalAppDimensions.dimen8),
    )
}

@Composable
private fun AddFeedUrlField(value: String, onValueChange: (String) -> Unit, brush: Brush, isValidUrl: Boolean) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.add_feed_url_text_field_label)) },
        textStyle = TextStyle(brush = brush),
        modifier = Modifier.padding(LocalAppDimensions.dimen8),
        isError = value.isNotEmpty() && !isValidUrl,
        supportingText = {
            if (value.isNotEmpty() && !isValidUrl) {
                Text(stringResource(R.string.add_feed_url_text_field_supporting_text))
            }
        }
    )
}

@Composable
private fun AddFeedButton(enabled: Boolean, onClick: () -> Unit) {
    ElevatedButton(
        modifier = Modifier.padding(LocalAppDimensions.dimen8),
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.20f),
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.20f)
        ),
        enabled = enabled,
        onClick = onClick
    ) {
        Text(stringResource(R.string.add_feed_button))
    }
}

@Composable
private fun LoadingOverlay() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(enabled = false) {}
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator()
    }
}