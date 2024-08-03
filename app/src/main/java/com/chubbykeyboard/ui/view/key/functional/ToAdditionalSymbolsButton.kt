package com.chubbykeyboard.ui.view.key.functional

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import com.chubbykeyboard.keyboard.keys.FunctionalKey

@Composable
fun RowScope.ToAdditionalSymbolsButton(
    key: FunctionalKey,
    onPress: () -> Unit,
) {
    BaseFunctionalButton(
        key = key,
        onClick = onPress,
    )
}