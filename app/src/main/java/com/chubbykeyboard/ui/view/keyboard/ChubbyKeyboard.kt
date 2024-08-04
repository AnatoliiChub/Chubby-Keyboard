package com.chubbykeyboard.ui.view.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chubbykeyboard.data.parser.KeyMatrix
import com.chubbykeyboard.keyboard.KeyBoardState
import com.chubbykeyboard.ui.theme.BackgroundColor
import com.chubbykeyboard.ui.theme.ChubbyKeyboardTheme


@Composable
fun ChubbyKeyboard(
    viewModel: ChubbyKeyboardViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    ChubbyKeyboardTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(228.dp)
                .background(BackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            when (val content = state.value) {
                KeyBoardState.Loading -> CircularProgressIndicator(modifier = Modifier.size(96.dp))
                is KeyBoardState.Content -> {
                    if (content.keyMatrix is KeyMatrix.NumPadMatrix) {
                        Numpad(content.keyMatrix, viewModel.router)
                    } else {
                        Keyboard(content, viewModel.router)
                    }
                }
            }
        }
    }
}






