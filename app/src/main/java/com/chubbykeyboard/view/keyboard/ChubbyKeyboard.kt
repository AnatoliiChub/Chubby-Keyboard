package com.chubbykeyboard.view.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chubbykeyboard.ChubbyIMEService
import com.chubbykeyboard.view.key.FunctionalKey
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
    viewModel: ChubbyKeyboardViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val isCapsLock = state.value.isCapsLockActive
    val service = (LocalContext.current as ChubbyIMEService)

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
            state.value.keyMatrix.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                ) {
                    row.forEach { key ->
                        when (key) {
                            is PrintedKey -> PrintedKeyButton(key, isCapsLock)
                            is FunctionalKey.CapsLock -> CapsLockButton(key, isCapsLock) {
                                viewModel.onCapsLockPressed()
                            }

                            is FunctionalKey.Backspace -> BackSpaceButton(key) {
                                service.currentInputConnection.deleteSurroundingText(1, 0)
                            }

                            is FunctionalKey.Enter -> EnterButton(key) {
                                service.sendKeyChar('\n')
                            }

                            is FunctionalKey.ToSymbols -> ToSymbolsButton(key) {
                                // TODO: Switch to symbols keyboard
                            }

                            is FunctionalKey.SwitchLanguage -> SwitchLanguageButton(key) {
                                // TODO: Switch to next language
                            }

                            is FunctionalKey.Space -> SpaceButton(key) {
                                service.sendKeyChar(' ')
                            }
                        }
                    }
                }
            }
        }
    }
}
