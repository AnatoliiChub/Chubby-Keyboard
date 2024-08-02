package com.chubbykeyboard.view.key.functional

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import com.chubbykeyboard.view.key.FunctionalKey

@Composable
fun RowScope.ToSLettersButton(
    key: FunctionalKey,
    onPress: () -> Unit,
) {
    BaseFunctionalButton(
        key = key,
        onClick = onPress,
    )
}