package com.chubbykeyboard.ui.view.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chubbykeyboard.ChubbyIMEService
import com.chubbykeyboard.keyboard.keys.Functional.*
import com.chubbykeyboard.ui.theme.BackgroundColor
import com.chubbykeyboard.ui.theme.ChubbyKeyboardTheme

import com.chubbykeyboard.keyboard.keys.FunctionalKey
import com.chubbykeyboard.keyboard.keys.FunctionalKey.CapsLock
import com.chubbykeyboard.keyboard.KeyBoardState
import com.chubbykeyboard.keyboard.keys.PrintedKey
import com.chubbykeyboard.ui.view.key.PrintedKeyButton
import com.chubbykeyboard.ui.view.key.functional.*


@Composable
fun ChubbyKeyboard(
    viewModel: ChubbyKeyboardViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    ChubbyKeyboardTheme {
        when (state.value) {
            KeyBoardState.Loading -> LoadingBox()
            is KeyBoardState.Content -> Keyboard(
                state.value as KeyBoardState.Content,
                onCapsLockPressed = viewModel::onCapsLockPressed,
                onSwitchLangPressed = viewModel::switchLanguage,
                onToSymbolsPressed = viewModel::onToSymbolsPressed,
                onToLettersPressed = viewModel::onToLettersPressed
            )
        }
    }
}

@Composable
private fun LoadingBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(288.dp)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.size(96.dp))
    }
}

@Composable
private fun Keyboard(
    state: KeyBoardState.Content,
    onCapsLockPressed: () -> Unit,
    onSwitchLangPressed: () -> Unit,
    onToSymbolsPressed: () -> Unit,
    onToLettersPressed: () -> Unit
) {
    val isCapsLock = state.isCapsLockActive
    val service = (LocalContext.current as ChubbyIMEService)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
        ) {
            state.keyMatrix.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                ) {
                    row.forEach { key ->
                        when (key) {
                            is PrintedKey -> PrintedKeyButton(key, isCapsLock)
                            is CapsLock -> CapsLockButton(key, isCapsLock) { onCapsLockPressed() }
                            is FunctionalKey -> {
                                when (key.function) {
                                    Backspace -> BackSpaceButton(key) {
                                        service.currentInputConnection.deleteSurroundingText(1, 0)
                                    }

                                    Enter -> EnterButton(key) { service.sendKeyChar('\n') }
                                    SwitchLanguage -> SwitchLanguageButton(key) { onSwitchLangPressed() }
                                    Space -> SpaceButton(key) { service.sendKeyChar(' ') }
                                    ToSymbols -> ToSymbolsButton(key) { onToSymbolsPressed() }
                                    ToLetters -> ToSLettersButton(key) { onToLettersPressed() }
                                    ToAdditionalSymbols -> ToAdditionalSymbolsButton(key) {
                                        // TODO: Implement listener
                                    }

                                    ToNumPad -> ToNumPadButton(key) {
                                        // TODO: Implement listener
                                    }

                                    //Already handled
                                    CapsLock -> Unit
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}