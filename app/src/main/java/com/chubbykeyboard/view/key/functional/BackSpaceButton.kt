package com.chubbykeyboard.view.key.functional

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.view.key.FunctionalKey

@Composable
fun RowScope.BackSpaceButton(
    key: FunctionalKey,
    onPress: () -> Unit,
) {
    BaseFunctionalButton(
        key = key,
        fontSize = 24.sp,
        fontWeight = Bold,
        onClick = onPress,
    )
}

