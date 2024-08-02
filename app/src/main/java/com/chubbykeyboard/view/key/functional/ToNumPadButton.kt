package com.chubbykeyboard.view.key.functional

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.view.key.FunctionalKey

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