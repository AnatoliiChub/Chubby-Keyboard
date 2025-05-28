package com.chubbykeyboard.ui.view.key.functional

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.chubbykeyboard.domain.keyboard.keys.FunctionalKey

@Composable
fun ToSymbolsButton(
    key: FunctionalKey,
    debounceInterval: Long,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    textColor: Color = MaterialTheme.colorScheme.onSecondary,
    onPress: () -> Unit
) {
    BaseFunctionalButton(
        key = key,
        backgroundColor = backgroundColor,
        textColor = textColor,
        debounceInterval = debounceInterval,
        onClick = onPress
    )
}
