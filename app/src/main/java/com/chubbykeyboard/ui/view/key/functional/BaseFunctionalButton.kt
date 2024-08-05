package com.chubbykeyboard.ui.view.key.functional


import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Normal
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chubbykeyboard.keyboard.keys.FunctionalKey
import com.chubbykeyboard.ui.theme.RippleAlpha
import com.chubbykeyboard.util.debounceClickable

@Composable
fun BaseFunctionalButton(
    key: FunctionalKey,
    fontSize: TextUnit = 20.sp,
    maxLines: Int = 1,
    fontWeight: FontWeight = Normal,
    onClick: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    textColor: Color = MaterialTheme.colorScheme.onSecondary
) {
    val interactionSource = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(8.dp)

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .clip(shape)
            .debounceClickable(
                interactionSource = interactionSource,
                indication = rememberRipple(true, color = textColor.copy(alpha = RippleAlpha)),
                onClick = onClick
            )
            .background(
                color = backgroundColor,
                shape = shape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            maxLines = maxLines,
            textAlign = TextAlign.Center,
            text = key.label,
            fontSize = fontSize,
            fontWeight = fontWeight,
            lineHeight = fontSize,
            color = textColor
        )
    }
}
