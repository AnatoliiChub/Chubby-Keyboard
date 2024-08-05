package com.chubbykeyboard.ui.view.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.chubbykeyboard.keyboard.KeyboardConst.Companion.NO_INPUT
import com.chubbykeyboard.ui.view.popup.positioinprovider.TrackablePositionProvider
import com.chubbykeyboard.util.ceilDiv

private const val POPUP_VERTICAL_OFFSET = -64

@Composable
fun AlternativesPopup(
    keys: List<String>,
    dragGesturePosition: Offset,
    buttonOffset: Offset,
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
            buttonOffset.x - popupOffset.value.x,
            buttonOffset.y - popupOffset.value.y
        )
        Box {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                keys.chunked((keys.size.ceilDiv(rowNumber))).forEach { row ->
                    Row {
                        row.forEach { letter ->
                            Box(modifier = Modifier.padding(4.dp)) {
                                AlternativeLetter(
                                    letter,
                                    dragGesturePosition,
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
