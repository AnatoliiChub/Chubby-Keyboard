package com.chubbykeyboard.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.chubbykeyboard.KeyboardConst.Companion.DEBOUNCE

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.debounceCombinedClickable(
    interactionSource: MutableInteractionSource,
    indication: Indication? = null,
    debounceInterval: Long = DEBOUNCE,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
): Modifier {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    return this.composed {
        Modifier.combinedClickable(interactionSource = interactionSource, indication = indication, onClick = {
            val currentTime = System.currentTimeMillis()
            if ((currentTime - lastClickTime) < debounceInterval) return@combinedClickable
            lastClickTime = currentTime
            onClick()
        }, onLongClick = onLongClick)
    }
}

@Composable
fun Modifier.debounceClickable(
    interactionSource: MutableInteractionSource,
    indication: Indication? = null,
    debounceInterval: Long = DEBOUNCE,
    onClick: () -> Unit,
): Modifier {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    return this.composed {
        Modifier.clickable(interactionSource = interactionSource, indication = indication, onClick = {
            val currentTime = System.currentTimeMillis()
            if ((currentTime - lastClickTime) < debounceInterval) return@clickable
            lastClickTime = currentTime
            onClick()
        })
    }
}
