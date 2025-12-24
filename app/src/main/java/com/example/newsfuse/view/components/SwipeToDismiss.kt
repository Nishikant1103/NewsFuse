package com.example.newsfuse.view.components

import androidx.annotation.NonNull
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ListItem
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun <T> SwipeToDismiss(
    @NonNull item: T,
    itemComposable: @Composable () -> Unit,
    onStartToEndSwiped: (T) -> Unit,
    onEndToStartSwiped: (T) -> Unit,
    leftComposable: @Composable () -> Unit,
    rightComposable: @Composable () -> Unit,
) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.StartToEnd) {
                onStartToEndSwiped(item)
            } else if (it == SwipeToDismissBoxValue.EndToStart) {
                onEndToStartSwiped(item)
            }
            true
        }
    )

    LaunchedEffect(swipeToDismissBoxState.currentValue) {
        if (swipeToDismissBoxState.currentValue != SwipeToDismissBoxValue.Settled) {
            swipeToDismissBoxState.reset()
        }
    }

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = Modifier.fillMaxSize(),
        backgroundContent = {
            when (swipeToDismissBoxState.dismissDirection) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    leftComposable()
                }

                SwipeToDismissBoxValue.EndToStart -> {
                    rightComposable()
                }

                SwipeToDismissBoxValue.Settled -> {}
            }
        }) {
        ListItem(
            { itemComposable() }
        )
    }
}