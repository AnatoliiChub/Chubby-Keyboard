package com.chubbykeyboard.ui.theme

import androidx.compose.ui.graphics.Brush

//TODO remove gradients
val PrintedKeyBrush: (Boolean) -> Brush = { pressed ->
    Brush.horizontalGradient(
        if (pressed) listOf(
            PressedPrintedKeyGradientStart,
            PressedPrintedKeyGradientEnd
        ) else listOf(
            PrintedKeyGradientStart,
            PrintedKeyGradientEnd
        )
    )
}

val AlternativeLetterKeyBrush: (Boolean) -> Brush = { pressed ->
    Brush.horizontalGradient(
        if (pressed) listOf(
            AlternativePressedKeyGradientStart,
            AlternativeKeyGradientEnd
        ) else listOf(
            AlternativeBackground,
            AlternativeBackground
        )
    )
}

val FunctionalKeyBrush: (Boolean) -> Brush = { pressed ->
    Brush.horizontalGradient(
        if (pressed) listOf(
            PressedFunctionalKeyGradientStart,
            PressedFunctionalKeyGradientEnd
        ) else listOf(
            FunctionalKeyGradientStart,
            FunctionalKeyGradientEnd
        )
    )
}

val EnterKeyBrush: (Boolean) -> Brush = { pressed ->
    Brush.horizontalGradient(
        if (pressed) listOf(
            PressedMainKeyGradientStart,
            PressedMainKeyGradientEnd
        ) else listOf(
            MainKeyGradientStart,
            MainKeyGradientEnd
        )
    )
}
