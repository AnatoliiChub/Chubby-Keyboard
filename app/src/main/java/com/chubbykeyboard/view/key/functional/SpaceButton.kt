package com.chubbykeyboard.view.key.functional

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.view.key.FunctionalKey

@Composable
fun RowScope.SpaceButton(
    key: FunctionalKey.Space,
    onPress: () -> Unit,
) {
    BaseFunctionalButton(
        key = key,
        weight = 2.5f,
        fontSize = 36.sp,
        fontWeight = Bold,
        onClick = onPress,
    )
}