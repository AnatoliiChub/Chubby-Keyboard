package com.chubbykeyboard.ui.view.key.functional

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.domain.keyboard.keys.FunctionalKey

@Composable
fun BackSpaceButton(
    key: FunctionalKey,
    debounceInterval: Long,
    onPress: () -> Unit,
) {
    BaseFunctionalButton(
        key = key,
        fontSize = 24.sp,
        fontWeight = Bold,
        debounceInterval = debounceInterval,
        onClick = onPress
    )
}
