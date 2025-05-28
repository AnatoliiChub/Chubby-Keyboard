package com.chubbykeyboard.ui.view.key.functional

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.domain.keyboard.keys.FunctionalKey

@Composable
fun CapsLockButton(
    key: FunctionalKey.CapsLock,
    isShiftedParam: Boolean,
    debounceInterval: Long,
    onPress: () -> Unit,
) {
    key.updateShift(isShiftedParam)

    BaseFunctionalButton(
        key = key,
        fontSize = 28.sp,
        fontWeight = Bold,
        debounceInterval = debounceInterval,
        onClick = onPress,
    )
}
