package com.chubbykeyboard.ui.view.keyboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.chubbykeyboard.keyboard.KeyBoardState
import com.chubbykeyboard.keyboard.keys.Functional.Backspace
import com.chubbykeyboard.keyboard.keys.Functional.CapsLock
import com.chubbykeyboard.keyboard.keys.Functional.Enter
import com.chubbykeyboard.keyboard.keys.Functional.Space
import com.chubbykeyboard.keyboard.keys.Functional.SwitchLanguage
import com.chubbykeyboard.keyboard.keys.Functional.ToAdditionalSymbols
import com.chubbykeyboard.keyboard.keys.Functional.ToLetters
import com.chubbykeyboard.keyboard.keys.Functional.ToNumPad
import com.chubbykeyboard.keyboard.keys.Functional.ToSymbols
import com.chubbykeyboard.keyboard.keys.FunctionalKey
import com.chubbykeyboard.keyboard.keys.PrintedKey
import com.chubbykeyboard.service.ChubbyIMEService
import com.chubbykeyboard.ui.view.key.PrintedKeyButton
import com.chubbykeyboard.ui.view.key.functional.BackSpaceButton
import com.chubbykeyboard.ui.view.key.functional.CapsLockButton
import com.chubbykeyboard.ui.view.key.functional.EnterButton
import com.chubbykeyboard.ui.view.key.functional.SpaceButton
import com.chubbykeyboard.ui.view.key.functional.SwitchLanguageButton
import com.chubbykeyboard.ui.view.key.functional.ToAdditionalSymbolsButton
import com.chubbykeyboard.ui.view.key.functional.ToLettersButton
import com.chubbykeyboard.ui.view.key.functional.ToNumPadButton
import com.chubbykeyboard.ui.view.key.functional.ToSymbolsButton

@Composable
fun Keyboard(
    state: KeyBoardState.Content, router: FunctionalRouter
) {
    val isCapsLock = state.isCapsLockActive
    val matrix = state.keyMatrix.matrix

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp)
                .fillMaxWidth()
        ) {
            matrix.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                ) {
                    row.forEach { key ->
                        when (key) {
                            is PrintedKey -> PrintedKeyLayout(key, isCapsLock)
                            is FunctionalKey -> FunctionalKeyLayout(key, router, isCapsLock)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.PrintedKeyLayout(key: PrintedKey, isCapsLock: Boolean) {
    Box(
        modifier = Modifier.Companion
            .weight(1f)
            .padding(2.dp)
    ) {
        PrintedKeyButton(
            key, isCapsLock
        )
    }
}

@Composable
private fun RowScope.FunctionalKeyLayout(
    key: FunctionalKey,
    router: FunctionalRouter,
    isCapsLock: Boolean,
) {
    val service = LocalContext.current as ChubbyIMEService

    val onSpacePressed = { service.sendKeyChar(' ') }
    val onEnterPressed = { service.sendKeyChar('\n') }
    val onBackspacePressed = { service.currentInputConnection.deleteSurroundingText(1, 0) }

    Box(
        modifier = Modifier.Companion
            .weight(
                when (key.function) {
                    Space -> 2.5f
                    ToSymbols, ToLetters, ToAdditionalSymbols, Enter -> 1.5f
                    else -> 1f
                }
            )
            .padding(2.dp)
    ) {
        when (key.function) {
            Backspace -> BackSpaceButton(key) { onBackspacePressed() }
            Enter -> EnterButton(key) { onEnterPressed() }
            SwitchLanguage -> SwitchLanguageButton(key) { router.onSwitchLangPressed() }
            Space -> SpaceButton(key) { onSpacePressed() }
            ToSymbols -> ToSymbolsButton(key) { router.onToSymbolsPressed() }
            ToLetters -> ToLettersButton(key) { router.onToLettersPressed() }
            ToAdditionalSymbols -> ToAdditionalSymbolsButton(key) { router.onToAdditionalSymbolsPressed() }
            ToNumPad -> ToNumPadButton(key) { router.onToNumPadPressed() }
            CapsLock -> CapsLockButton(key as FunctionalKey.CapsLock, isCapsLock) { router.onCapsLockPressed() }
        }
    }
}

