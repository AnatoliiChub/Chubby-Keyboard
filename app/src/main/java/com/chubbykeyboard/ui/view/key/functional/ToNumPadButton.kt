package com.chubbykeyboard.ui.view.key.functional

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.domain.keyboard.keys.FunctionalKey

@Composable
fun ToNumPadButton(
    key: FunctionalKey,
    debounceInterval: Long,
    onPress: () -> Unit,
) {
    BaseFunctionalButton(
        key = key,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 2,
        debounceInterval = debounceInterval,
        onClick = onPress,
    )
}
