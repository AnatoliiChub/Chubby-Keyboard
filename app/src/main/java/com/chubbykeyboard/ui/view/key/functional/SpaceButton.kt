package com.chubbykeyboard.ui.view.key.functional

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.keyboard.keys.FunctionalKey

@Composable
fun SpaceButton(
    key: FunctionalKey,
    onPress: () -> Unit
) {
    BaseFunctionalButton(
        key = key,
        fontSize = 14.sp,
        onClick = onPress,
    )
}