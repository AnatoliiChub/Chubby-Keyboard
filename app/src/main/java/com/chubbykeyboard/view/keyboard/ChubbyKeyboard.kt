package com.chubbykeyboard.view.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chubbykeyboard.view.KeyButton

@Composable
fun ChubbyKeyboard(
    viewModel: ChubbyKeyboardViewModel = hiltViewModel()
) {
    val isShifted = rememberSaveable { mutableStateOf(false) }

    val keyGrid = viewModel.currentKeyGrid.collectAsState()

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
            keyGrid.value.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                ) {
                    row.forEach { key -> KeyButton(key, isShifted.value) { viewModel.onShiftPressed() } }
                }
            }
        }
    }
}