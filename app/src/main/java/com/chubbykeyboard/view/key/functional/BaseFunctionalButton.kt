package com.chubbykeyboard.view.key.functional


import androidx.compose.foundation.background
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.debounceClickable
import com.chubbykeyboard.view.key.FunctionalKey

@Composable
fun RowScope.BaseFunctionalButton(
    key: FunctionalKey,
    weight: Float = 1f,
    fontSize: TextUnit = 20.sp,
    fontWeight: FontWeight = Normal,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .weight(weight)
            .debounceClickable(
                interactionSource = interactionSource,
                indication = rememberRipple(true),
                onClick = onClick
            )
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
            fontSize = fontSize,
            fontWeight = fontWeight
        )
    }
}

@Composable
private fun keyBrush(
    pressed: State<Boolean>
) = Brush.horizontalGradient(
    if (pressed.value) listOf(
        Color.LightGray,
        Color.DarkGray
    ) else listOf(
        Color.White,
        Color.LightGray
    )
)
