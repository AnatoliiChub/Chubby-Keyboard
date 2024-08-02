package com.chubbykeyboard.view.key.functional


import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.debounceClickable
import com.chubbykeyboard.ui.theme.FunctionalKeyBrush
import com.chubbykeyboard.view.key.FunctionalKey

@Composable
fun RowScope.BaseFunctionalButton(
    key: FunctionalKey,
    weight: Float = 1.5f,
    fontSize: TextUnit = 20.sp,
    maxLines : Int = 1,
    fontWeight: FontWeight = Normal,
    onClick: () -> Unit,
    backgroundBrushProvider: (Boolean) -> Brush = FunctionalKeyBrush
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .weight(weight)
            .padding(2.dp)
            .debounceClickable(
                interactionSource = interactionSource,
                indication = rememberRipple(true),
                onClick = onClick
            )
            .background(
                brush = backgroundBrushProvider.invoke(pressed.value),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            maxLines = maxLines,
            textAlign = TextAlign.Center,
            text = key.label,
            fontSize = fontSize,
            fontWeight = fontWeight,
            lineHeight = fontSize
        )
    }
}
