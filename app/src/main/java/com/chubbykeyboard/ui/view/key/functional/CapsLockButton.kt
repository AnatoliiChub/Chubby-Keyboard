package com.chubbykeyboard.ui.view.key.functional

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.FunctionalKey

@Composable
fun RowScope.CapsLockButton(
    key: FunctionalKey.CapsLock,
    isShiftedParam: Boolean,
    onPress: () -> Unit,
) {
    key.updateShift(isShiftedParam)

    BaseFunctionalButton(
        key = key,
        fontSize = 28.sp,
        fontWeight = Bold,
        onClick = onPress,
    )
}

