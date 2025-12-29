package com.example.newsfuse.core.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val dimen2: Dp = 2.dp,
    val dimen4: Dp = 4.dp,
    val dimen8: Dp = 8.dp,
    val dimen16: Dp = 16.dp,
    val dimen32: Dp = 32.dp,
    val dimen64: Dp = 64.dp
)



val LocalAppDimensions = Dimensions()