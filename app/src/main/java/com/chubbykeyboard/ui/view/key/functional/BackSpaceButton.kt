package com.chubbykeyboard.ui.view.key.functional

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.keyboard.keys.FunctionalKey

@Composable
fun BackSpaceButton(
    key: FunctionalKey,
    onPress: () -> Unit
) {
    BaseFunctionalButton(
        key = key,
        fontSize = 24.sp,
        fontWeight = Bold,
        onClick = onPress,
    )
}

