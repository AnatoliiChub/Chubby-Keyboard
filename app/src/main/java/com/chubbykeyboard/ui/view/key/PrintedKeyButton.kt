package com.chubbykeyboard.ui.view.key

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Offset.Companion.Zero
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.domain.keyboard.KeyboardConst.Companion.NO_INPUT
import com.chubbykeyboard.domain.keyboard.keys.PrintedKey
import com.chubbykeyboard.service.ChubbyIMEService
import com.chubbykeyboard.ui.theme.ROUNDED_CORNERS_RADIUS
import com.chubbykeyboard.ui.theme.RippleAlpha
import com.chubbykeyboard.ui.view.popup.AlternativesPopup
import com.chubbykeyboard.util.debounceCombinedClickable
import java.util.Locale

@Composable
fun PrintedKeyButton(
    key: PrintedKey,
    isShiftedParam: Boolean,
    debounceInterval: Long,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    val dragGesturePosition = remember { mutableStateOf(Zero) }
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val longPressed = remember { mutableStateOf(false) }
    val rootPosition = remember { mutableStateOf(Zero) }
    val selectedPromptLetter = remember { mutableStateOf(NO_INPUT) }
    val ctx = LocalContext.current
    if (key is PrintedKey.Letter) key.setCapital(isShiftedParam)

    val shape = RoundedCornerShape(ROUNDED_CORNERS_RADIUS)

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .clip(shape)
            .debounceCombinedClickable(
                interactionSource = interactionSource,
                indication = rememberRipple(true, color = textColor.copy(alpha = RippleAlpha)),
                debounceInterval = debounceInterval,
                onLongClick = { longPressed.value = true },
                onClick = { onPrintedKeyPressed(ctx, key.symbol, isShiftedParam) })
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress { change, _ ->
                    dragGesturePosition.value = change.position
                }
            }
            .onGloballyPositioned { rootPosition.value = it.localToRoot(Zero) }
            .background(
                color = backgroundColor,
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            maxLines = 1,
            textAlign = TextAlign.Center,
            text = key.symbol,
            fontSize = 24.sp,
            color = textColor
        )
        val shouldShowPopup = pressed.value && longPressed.value
        //TODO Consider to use Popup for click and AlertDialog for long press
        if (shouldShowPopup) {
            Popup(key, isShiftedParam, dragGesturePosition.value, rootPosition.value, selectedPromptLetter)
        } else {
            if (longPressed.value && selectedPromptLetter.value != NO_INPUT) {
                onPrintedKeyPressed(ctx, selectedPromptLetter.value, isShiftedParam)
            }
            longPressed.value = false
        }
    }
}

@Composable
private fun Popup(
    key: PrintedKey,
    isShiftedParam: Boolean,
    dragGesturePosition: Offset,
    rootPosition: Offset,
    selectedPromptLetter: MutableState<String>
) {
    val alternatives = key.alternatives?.map {
        if (isShiftedParam) it.uppercase() else it.toString()
    }
    AlternativesPopup(alternatives ?: listOf(key.symbol), dragGesturePosition, rootPosition) {
        selectedPromptLetter.value = it
    }
}

private fun onPrintedKeyPressed(
    ctx: Context,
    letter: String,
    isShiftedParam: Boolean
) {
    (ctx as ChubbyIMEService).currentInputConnection.commitText(
        if (isShiftedParam) letter.uppercase(Locale.getDefault()) else letter,
        letter.length
    )
}