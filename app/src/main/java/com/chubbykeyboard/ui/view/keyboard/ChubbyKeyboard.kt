package com.chubbykeyboard.ui.view.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chubbykeyboard.data.parser.KeyMatrix
import com.chubbykeyboard.domain.keyboard.KeyBoardState
import com.chubbykeyboard.ui.theme.ChubbyKeyboardTheme
import com.chubbykeyboard.ui.theme.KEYBOARD_HEIGHT
import com.chubbykeyboard.ui.theme.KEYBOARD_VERTICAL_PADDING
import com.chubbykeyboard.ui.theme.PROGRESS_BAR_SIZE

@Composable
fun ChubbyKeyboard(
    viewModel: ChubbyKeyboardViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    ChubbyKeyboardTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(KEYBOARD_HEIGHT)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = KEYBOARD_VERTICAL_PADDING)
            ) {
                when (val content = state.value) {
                    KeyBoardState.Loading -> CircularProgressIndicator(modifier = Modifier.size(PROGRESS_BAR_SIZE))
                    is KeyBoardState.Content -> {
                        if (content.keyMatrix is KeyMatrix.NumPadMatrix) {
                            Numpad(content, viewModel.router)
                        } else {
                            Keyboard(content, viewModel.router)
                        }
                    }
                }
            }
        }
    }
}
