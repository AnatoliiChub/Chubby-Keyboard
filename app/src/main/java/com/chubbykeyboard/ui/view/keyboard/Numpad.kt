package com.chubbykeyboard.ui.view.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.core.text.isDigitsOnly
import com.chubbykeyboard.data.parser.KeyMatrix
import com.chubbykeyboard.keyboard.KeyBoardState
import com.chubbykeyboard.keyboard.keys.Functional.Backspace
import com.chubbykeyboard.keyboard.keys.Functional.Enter
import com.chubbykeyboard.keyboard.keys.Functional.Space
import com.chubbykeyboard.keyboard.keys.Functional.ToLetters
import com.chubbykeyboard.keyboard.keys.Functional.ToSymbols
import com.chubbykeyboard.keyboard.keys.FunctionalKey
import com.chubbykeyboard.keyboard.keys.PrintedKey
import com.chubbykeyboard.service.ChubbyIMEService
import com.chubbykeyboard.ui.theme.ADDITIONAL_OPTIONS_BUTTON_HEIGHT
import com.chubbykeyboard.ui.theme.ADDITIONAL_OPTIONS_HEIGHT
import com.chubbykeyboard.ui.theme.BUTTON_HEIGHT
import com.chubbykeyboard.ui.theme.BUTTON_PADDING
import com.chubbykeyboard.ui.theme.ROUNDED_CORNERS_RADIUS
import com.chubbykeyboard.ui.view.key.PrintedKeyButton
import com.chubbykeyboard.ui.view.key.functional.BackSpaceButton
import com.chubbykeyboard.ui.view.key.functional.EnterButton
import com.chubbykeyboard.ui.view.key.functional.SpaceButton
import com.chubbykeyboard.ui.view.key.functional.ToLettersButton
import com.chubbykeyboard.ui.view.key.functional.ToSymbolsButton

@Composable
fun Numpad(
    state: KeyBoardState.Content, router: FunctionalRouter
) {
    val keyMatrix = state.keyMatrix as KeyMatrix.NumPadMatrix
    val debounce = state.debounce
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val additionalColumnWeight = 1f
        val mainColumnWeight = 4f
        Column(
            modifier = Modifier
                .weight(additionalColumnWeight)
                .fillMaxWidth()
        ) {
            val shape = RoundedCornerShape(ROUNDED_CORNERS_RADIUS)
            AdditionalOptionsPanel(shape, keyMatrix, debounce)
            AdditionalButton(keyMatrix.additionalButton, debounce, router.onToLettersPressed)
        }
        Column(
            modifier = Modifier
                .weight(mainColumnWeight)
                .fillMaxWidth()
        ) {
            keyMatrix.matrix.forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(BUTTON_HEIGHT),
                ) {
                    row.forEach { key ->
                        when (key) {
                            is PrintedKey -> PrintedKeyWrapper(key, debounce)
                            is FunctionalKey -> FunctionalKeyWrapper(key, debounce, router)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AdditionalOptionsPanel(
    shape: RoundedCornerShape,
    state: KeyMatrix.NumPadMatrix,
    debounce: Long
) {
    LazyColumn(
        modifier = Modifier
            .height(ADDITIONAL_OPTIONS_HEIGHT)
            .padding(BUTTON_PADDING)
            .clip(shape)
            .background(
                color = MaterialTheme.colorScheme.secondary, shape = shape
            )
    ) {
        item {
            state.additionalOptions.forEach { key ->
                Box(modifier = Modifier.height(ADDITIONAL_OPTIONS_BUTTON_HEIGHT)) {
                    PrintedKeyButton(
                        key,
                        false,
                        debounce,
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.onSecondary,
                    )
                }
            }
        }
    }
}

@Composable
private fun AdditionalButton(
    functionalKey: FunctionalKey,
    debounce: Long,
    onToLettersPressed: () -> Unit
) {
    functionalKey.let { key ->
        if (key.function == ToLetters) {
            Box(
                modifier = Modifier
                    .height(BUTTON_HEIGHT)
                    .padding(BUTTON_PADDING)
            ) {
                ToLettersButton(key, debounce) { onToLettersPressed() }
            }
        } else {
            throw IllegalStateException("Unsupported key for numpad: $key")
        }
    }
}

@Composable
private fun RowScope.FunctionalKeyWrapper(
    key: FunctionalKey,
    debounce: Long,
    router: FunctionalRouter
) {
    val service = (LocalContext.current as ChubbyIMEService)
    Box(
        modifier = Modifier.Companion
            .weight(if (key.function == ToSymbols) 0.5f else 1f)
            .padding(BUTTON_PADDING)
    ) {
        when (key.function) {
            Backspace -> BackSpaceButton(key, debounce) {
                service.currentInputConnection.deleteSurroundingText(1, 0)
            }

            Enter -> EnterButton(key, debounce) { service.sendKeyChar('\n') }
            Space -> SpaceButton(key, debounce) { service.sendKeyChar(' ') }
            ToSymbols -> ToSymbolsButton(key, debounce) { router.onToSymbolsPressed() }
            ToLetters -> ToLettersButton(key, debounce) { router.onToLettersPressed() }
            else -> throw IllegalStateException("Unsupported key for numpad: $key")
        }
    }
}

@Composable
private fun RowScope.PrintedKeyWrapper(key: PrintedKey, debounce: Long) {
    Box(
        modifier = Modifier.Companion
            .weight(if (key.symbol == "," || key.symbol == "." || key.symbol == "=") 0.5f else 1f)
            .padding(BUTTON_PADDING)
    ) {
        val backgroundColor =
            if (key.symbol.isDigitsOnly()) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.secondary

        PrintedKeyButton(
            key,
            false,
            debounce,
            backgroundColor = backgroundColor
        )
    }
}
