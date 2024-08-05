package com.chubbykeyboard.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val SurfaceColor: @Composable (Boolean) -> Color = { pressed ->
    if (pressed) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
}
