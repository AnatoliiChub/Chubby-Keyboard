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
import com.chubbykeyboard.domain.keyboard.KeyBoardState
import com.chubbykeyboard.domain.keyboard.keys.Functional.Backspace
import com.chubbykeyboard.domain.keyboard.keys.Functional.CapsLock
import com.chubbykeyboard.domain.keyboard.keys.Functional.Enter
import com.chubbykeyboard.domain.keyboard.keys.Functional.Space
import com.chubbykeyboard.domain.keyboard.keys.Functional.SwitchLanguage
import com.chubbykeyboard.domain.keyboard.keys.Functional.ToAdditionalSymbols
import com.chubbykeyboard.domain.keyboard.keys.Functional.ToLetters
import com.chubbykeyboard.domain.keyboard.keys.Functional.ToNumPad
import com.chubbykeyboard.domain.keyboard.keys.Functional.ToSymbols
import com.chubbykeyboard.domain.keyboard.keys.FunctionalKey
import com.chubbykeyboard.domain.keyboard.keys.PrintedKey
import com.chubbykeyboard.service.ChubbyIMEService
import com.chubbykeyboard.ui.theme.BUTTON_HEIGHT
import com.chubbykeyboard.ui.theme.BUTTON_PADDING
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
    val debounce = state.debounce
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        matrix.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(BUTTON_HEIGHT),
            ) {
                row.forEach { key ->
                    when (key) {
                        is PrintedKey -> PrintedKeyWrapper(key, isCapsLock, debounce)
                        is FunctionalKey -> FunctionalKeyWrapper(key, router, isCapsLock, debounce)
                    }
                }
            }
        }
    }
}

@Composable
private fun RowScope.PrintedKeyWrapper(key: PrintedKey, isCapsLock: Boolean, debounce: Long) {
    Box(
        modifier = Modifier.Companion
            .weight(1f)
            .padding(BUTTON_PADDING)
    ) {
        PrintedKeyButton(
            key, isCapsLock, debounce
        )
    }
}

@Composable
private fun RowScope.FunctionalKeyWrapper(
    key: FunctionalKey,
    router: FunctionalRouter,
    isCapsLock: Boolean,
    debounce: Long
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
            .padding(BUTTON_PADDING)
    ) {
        when (key.function) {
            Backspace -> BackSpaceButton(key, debounce) { onBackspacePressed() }
            Space -> SpaceButton(key, debounce) { onSpacePressed() }
            Enter -> EnterButton(key, debounce) { onEnterPressed() }
            ToSymbols -> ToSymbolsButton(key, debounce) { router.onToSymbolsPressed() }
            ToLetters -> ToLettersButton(key, debounce) { router.onToLettersPressed() }
            ToAdditionalSymbols -> ToAdditionalSymbolsButton(key, debounce) { router.onToAdditionalSymbolsPressed() }
            ToNumPad -> ToNumPadButton(key, debounce) { router.onToNumPadPressed() }
            CapsLock -> CapsLockButton(key as FunctionalKey.CapsLock, isCapsLock, debounce) {
                router.onCapsLockPressed()
            }
            SwitchLanguage -> SwitchLanguageButton(key, debounce) { router.onSwitchLangPressed() }
        }
    }
}

