package com.tapdoo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

object Spacing {
    val extraSmall = 4.dp
    val small = 8.dp
    val extraMedium = 12.dp
    val medium = 16.dp
    val large = 24.dp
    val extraLarge = 32.dp
}

val MaterialTheme.spacing: Spacing
    get() = Spacing