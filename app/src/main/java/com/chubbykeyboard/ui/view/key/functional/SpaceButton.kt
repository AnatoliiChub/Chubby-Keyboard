package com.chubbykeyboard.ui.view.key.functional

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.keyboard.keys.FunctionalKey

@Composable
fun SpaceButton(
    key: FunctionalKey,
    debounceInterval: Long,
    onPress: () -> Unit,
) {
    BaseFunctionalButton(
        key = key,
        fontSize = 14.sp,
        debounceInterval = debounceInterval,
        onClick = onPress,
    )
}