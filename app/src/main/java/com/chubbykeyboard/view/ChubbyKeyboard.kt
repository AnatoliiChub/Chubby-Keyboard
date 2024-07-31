package com.chubbykeyboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chubbykeyboard.view.model.FunctionalKey
import com.chubbykeyboard.view.model.PrintedKey

@Composable
fun ChubbyKeyboard() {
    val isShifted = rememberSaveable { mutableStateOf(false) }
    val onShiftPressed: () -> Boolean = {
        isShifted.value = !isShifted.value
        isShifted.value
    }

    val keyGrid = arrayOf(
        arrayOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p").map { PrintedKey.Letter(it) }.toTypedArray(),
        arrayOf("a", "s", "d", "f", "g", "h", "j", "k", "l").map { PrintedKey.Letter(it) }.toTypedArray(),
        arrayOf(
            FunctionalKey.Shift,
            PrintedKey.Letter("z"),
            PrintedKey.Letter("x"),
            PrintedKey.Letter("c"),
            PrintedKey.Letter("v"),
            PrintedKey.Letter("b"),
            PrintedKey.Letter("n"),
            PrintedKey.Letter("m"),
            FunctionalKey.Backspace
        ),
        arrayOf(
            FunctionalKey.ToNumber,
            PrintedKey.Letter(","),
            PrintedKey.Space,
            PrintedKey.Letter("."),
            FunctionalKey.Enter
        )
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 48.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            keyGrid.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                ) {
                    row.forEach { key -> KeyButton(key, isShifted.value, onShiftPressed) }
                }
            }
        }
    }
}