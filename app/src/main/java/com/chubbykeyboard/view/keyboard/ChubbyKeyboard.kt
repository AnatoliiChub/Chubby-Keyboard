package com.chubbykeyboard.view.keyboard

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
import com.chubbykeyboard.ui.theme.BackgroundColor
import com.chubbykeyboard.ui.theme.ChubbyKeyboardTheme
import com.chubbykeyboard.view.key.Functional.Backspace
import com.chubbykeyboard.view.key.Functional.Enter
import com.chubbykeyboard.view.key.Functional.Space
import com.chubbykeyboard.view.key.Functional.SwitchLanguage
import com.chubbykeyboard.view.key.Functional.ToSymbols
import com.chubbykeyboard.view.key.FunctionalKey
import com.chubbykeyboard.view.key.FunctionalKey.CapsLock
import com.chubbykeyboard.view.key.PrintedKey
import com.chubbykeyboard.view.key.PrintedKeyButton
import com.chubbykeyboard.view.key.functional.BackSpaceButton
import com.chubbykeyboard.view.key.functional.CapsLockButton
import com.chubbykeyboard.view.key.functional.EnterButton
import com.chubbykeyboard.view.key.functional.SpaceButton
import com.chubbykeyboard.view.key.functional.SwitchLanguageButton
import com.chubbykeyboard.view.key.functional.ToSymbolsButton

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
                onSwitchLangPressed = viewModel::switchLanguage
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
private fun Keyboard(state: KeyBoardState.Content, onCapsLockPressed: () -> Unit, onSwitchLangPressed: () -> Unit) {
    val isCapsLock = state.isCapsLockActive
    val service = (LocalContext.current as ChubbyIMEService)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 72.dp)
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
                                    ToSymbols -> ToSymbolsButton(key) {
                                        // TODO: Switch to symbols keyboard
                                    }
                                    SwitchLanguage -> SwitchLanguageButton(key) { onSwitchLangPressed() }
                                    Space -> SpaceButton(key) { service.sendKeyChar(' ') }
                                    else -> Unit
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}