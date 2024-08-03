package com.chubbykeyboard.ui.view.key.functional

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.keyboard.keys.FunctionalKey

@Composable
fun RowScope.ToNumPadButton(
    key: FunctionalKey,
    onPress: () -> Unit,
) {
    BaseFunctionalButton(
        key = key,
        onClick = onPress,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        maxLines = 2
    )
}