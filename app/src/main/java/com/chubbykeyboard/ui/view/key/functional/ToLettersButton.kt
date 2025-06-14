package com.chubbykeyboard.ui.view.key.functional

import androidx.compose.runtime.Composable
import com.chubbykeyboard.domain.keyboard.keys.FunctionalKey

@Composable
fun ToLettersButton(
    key: FunctionalKey,
    debounceInterval: Long,
    onPress: () -> Unit,
) {
    BaseFunctionalButton(
        key = key,
        debounceInterval = debounceInterval,
        onClick = onPress,
    )
}
