package com.chubbykeyboard.view.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Popup
import com.chubbykeyboard.KeyboardConst.Companion.NO_INPUT
import com.chubbykeyboard.view.model.PrintedKey
import com.chubbykeyboard.view.popup.positioinprovider.TrackablePositionProvider
import com.chubbykeyboard.view.screenSize

@Composable
fun AlternativeLetterPopup(
    keys: List<PrintedKey.Letter>,
    dragGesturePosition: MutableState<Offset>,
    buttonOffset: MutableState<Offset>,
    onSelected: (String) -> Unit
) {

    val popupOffset = remember {
        mutableStateOf(Offset.Zero)
    }

    val verticalOffset = -48

    Popup(
        popupPositionProvider = TrackablePositionProvider(
            alignment = Alignment.TopCenter,
            offset = IntOffset(0, verticalOffset),
            screenSize = LocalConfiguration.current.screenSize(),
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
        Row(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.LightGray)
        ) {
            keys.map { it.displayedSymbol }.forEach { letter ->
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
