package com.chubbykeyboard.ui.view.key.functional

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.domain.keyboard.keys.FunctionalKey

@Composable
fun EnterButton(
    key: FunctionalKey,
    debounceInterval: Long,
    onPress: () -> Unit,
) {
    BaseFunctionalButton(
        key = key,
        fontSize = 36.sp,
        fontWeight = Bold,
        debounceInterval = debounceInterval,
        backgroundColor = MaterialTheme.colorScheme.tertiary,
        textColor = MaterialTheme.colorScheme.onTertiary,
        onClick = onPress,
    )
}
