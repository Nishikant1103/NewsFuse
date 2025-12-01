package com.example.akhbaar.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val dimenTiny: Dp = 2.dp,
    val dimenSmall: Dp = 4.dp,
    val dimenMedium: Dp = 8.dp,
    val dimenLarge: Dp = 16.dp,
    val dimenBig: Dp = 32.dp,
)



val LocalAppDimensions = Dimensions()