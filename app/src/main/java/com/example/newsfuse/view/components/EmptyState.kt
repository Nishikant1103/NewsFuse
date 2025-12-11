package com.example.newsfuse.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.akhbaar.ui.theme.LocalAppDimensions

@Composable
fun EmptyState(image: Int, title: String, message: String) {
    // Implementation of the EmptyState composable
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(align = Alignment.Center)) {

        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(
                image
            )
        )

        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            restartOnPlay = false
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .padding(LocalAppDimensions.dimenLarge)
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalAppDimensions.dimenLarge),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = message,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalAppDimensions.dimenLarge),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}