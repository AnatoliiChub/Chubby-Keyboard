package com.chubbykeyboard.view.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Offset.Companion.Zero
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.ui.theme.AlternativeLetterKeyBrush

@Composable
fun AlternativeLetter(
    letter: String,
    dragGesturePosition: Offset = Zero,
    popupOffset: Offset,
    onSelected: () -> Unit
) {
    val bounds = remember {
        mutableStateOf(Rect(0f, 0f, 0f, 0f))
    }
    val selected = bounds.value.contains(dragGesturePosition)
    if (selected) {
        onSelected.invoke()
    }
    Box(
        modifier = Modifier
            .background(
                brush = AlternativeLetterKeyBrush.invoke(selected),
                shape = CircleShape
            )
            .onGloballyPositioned {
                val offset = it.localToWindow(Zero)
                bounds.value = Rect(
                    offset.x - popupOffset.x,
                    offset.y - popupOffset.y,
                    offset.x + it.size.width - popupOffset.x,
                    offset.y + it.size.height - popupOffset.y
                )
            },
    ) {
        Text(
            maxLines = 1,
            modifier = Modifier
                .padding(6.dp)
                .size(40.dp),
            textAlign = TextAlign.Center,
            text = letter,
            fontSize = 24.sp
        )
    }
}