package com.chubbykeyboard.ui.view.key.functional

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.keyboard.keys.FunctionalKey

@Composable
fun RowScope.SpaceButton(
    key: FunctionalKey,
    onPress: () -> Unit,
) {
    BaseFunctionalButton(
        key = key,
        weight = 2.5f,
        fontSize = 14.sp,
        onClick = onPress,
    )
}