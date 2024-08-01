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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    val isShifted = rememberSaveable { mutableStateOf(false) }

    val keyGrid = viewModel.currentKeyGrid.collectAsState()
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
            keyGrid.value.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                ) {
                    row.forEach { key ->
                        when (key) {
                            is PrintedKey -> PrintedKeyButton(key, isShifted.value)
                            is FunctionalKey.CapsLock -> CapsLockButton(key, isShifted.value) {
                                isShifted.value = !isShifted.value
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
