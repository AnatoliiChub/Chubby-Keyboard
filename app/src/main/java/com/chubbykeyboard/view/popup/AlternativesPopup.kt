package com.chubbykeyboard.view.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.chubbykeyboard.KeyboardConst.Companion.NO_INPUT
import com.chubbykeyboard.ceilDiv
import com.chubbykeyboard.ui.theme.AlternativeBackground
import com.chubbykeyboard.view.key.PrintedKey
import com.chubbykeyboard.view.popup.positioinprovider.TrackablePositionProvider

private const val POPUP_VERTICAL_OFFSET = -64

@Composable
fun AlternativesPopup(
    keys: List<PrintedKey.Letter>,
    dragGesturePosition: MutableState<Offset>,
    buttonOffset: MutableState<Offset>,
    onSelected: (String) -> Unit
) {
    val popupOffset = remember {
        mutableStateOf(Offset.Zero)
    }
    val rowNumber = calculateRowNumbers(keys.size)

    Popup(
        popupPositionProvider = TrackablePositionProvider(
            alignment = Alignment.TopCenter,
            offset = IntOffset(0, POPUP_VERTICAL_OFFSET * rowNumber),
            onPopupPositionChanged = {
                popupOffset.value = it
            },
        )
    ) {
        onSelected.invoke(NO_INPUT)
        val popupRelativeOffset = Offset(
            buttonOffset.value.x - popupOffset.value.x,
            buttonOffset.value.y - popupOffset.value.y
        )
        Box {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .background(
                        color = AlternativeBackground,
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                keys.chunked((keys.size.ceilDiv(rowNumber))).forEach { row ->
                    Row {
                        row.map { it.symbol }.forEach { letter ->
                            Box(modifier = Modifier.padding(4.dp)) {
                                AlternativeLetter(
                                    letter,
                                    dragGesturePosition.value,
                                    popupRelativeOffset
                                ) {
                                    onSelected.invoke(letter)
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}


private fun calculateRowNumbers(size: Int) = when (size) {
    in 1..3 -> 1
    in 4..8 -> 2
    else -> 3
}
