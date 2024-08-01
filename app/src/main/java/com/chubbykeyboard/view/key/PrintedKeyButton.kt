package com.chubbykeyboard.view.key

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset.Companion.Zero
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.ChubbyIMEService
import com.chubbykeyboard.KeyboardConst.Companion.NO_INPUT
import com.chubbykeyboard.debounceCombinedClickable
import com.chubbykeyboard.view.popup.AlternativeLetterPopup
import java.util.Locale

@Composable
fun RowScope.PrintedKeyButton(
    key: PrintedKey,
    isShiftedParam: Boolean,
) {
    val dragGesturePosition = remember { mutableStateOf(Zero) }
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val longPressed = remember { mutableStateOf(false) }
    val rootPosition = remember { mutableStateOf(Zero) }
    val selectedPromptLetter = remember { mutableStateOf(NO_INPUT) }
    val ctx = LocalContext.current
    if (key is PrintedKey.Letter) {
        key.setCapital(isShiftedParam)
    }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .weight(if (pressed.value) 1.5f else 1f)
            .debounceCombinedClickable(
                interactionSource = interactionSource,
                indication = rememberRipple(true),
                onLongClick = { longPressed.value = true },
                onClick = { onPrintedKeyPressed(ctx, key.printedSymbol, isShiftedParam) })
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress { change, _ ->
                    dragGesturePosition.value = change.position
                }
            }
            .onGloballyPositioned { rootPosition.value = it.localToRoot(Zero) }
            .background(
                brush = keyBrush(pressed),
                shape = RoundedCornerShape(15.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            maxLines = 1,
            textAlign = TextAlign.Center,
            text = key.displayedSymbol,
            fontSize = 24.sp
        )
        if (pressed.value && longPressed.value) {
            //todo: fix hardcoded values
            val hardCodedLetters =
                listOf("q", "w", "e").map { PrintedKey.Letter(it) }.onEach { it.setCapital(isShiftedParam) }

            AlternativeLetterPopup(hardCodedLetters, dragGesturePosition, rootPosition, onSelected = {
                selectedPromptLetter.value = it
            })

        } else {
            if (longPressed.value && selectedPromptLetter.value != NO_INPUT) {
                onPrintedKeyPressed(ctx, selectedPromptLetter.value, isShiftedParam)
            }
            longPressed.value = false
        }
    }
}

@Composable
private fun keyBrush(
    pressed: State<Boolean>
) = Brush.horizontalGradient(
    if (pressed.value) listOf(
        Color.White,
        Color.LightGray
    ) else listOf(
        Color.White,
        Color.White
    )
)

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