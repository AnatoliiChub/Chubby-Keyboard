package com.chubbykeyboard.ui.view.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.chubbykeyboard.data.parser.KeyMatrix
import com.chubbykeyboard.keyboard.keys.Functional.Backspace
import com.chubbykeyboard.keyboard.keys.Functional.Enter
import com.chubbykeyboard.keyboard.keys.Functional.Space
import com.chubbykeyboard.keyboard.keys.Functional.ToLetters
import com.chubbykeyboard.keyboard.keys.Functional.ToSymbols
import com.chubbykeyboard.keyboard.keys.FunctionalKey
import com.chubbykeyboard.keyboard.keys.PrintedKey
import com.chubbykeyboard.service.ChubbyIMEService
import com.chubbykeyboard.ui.view.key.PrintedKeyButton
import com.chubbykeyboard.ui.view.key.functional.BackSpaceButton
import com.chubbykeyboard.ui.view.key.functional.EnterButton
import com.chubbykeyboard.ui.view.key.functional.SpaceButton
import com.chubbykeyboard.ui.view.key.functional.ToLettersButton
import com.chubbykeyboard.ui.view.key.functional.ToSymbolsButton

//TODO fix styles and colors
@Composable
fun Numpad(
    state: KeyMatrix.NumPadMatrix, router: FunctionalRouter
) {
    val service = (LocalContext.current as ChubbyIMEService)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp)
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                val shape = RoundedCornerShape(6.dp)

                LazyColumn(
                    modifier = Modifier
                        .height(168.dp)
                        .padding(2.dp)
                        .clip(shape)
                        .background(
                            color = MaterialTheme.colorScheme.secondary, shape = shape
                        )
                ) {
                    item {
                        state.additionalOptions.forEach { key ->
                            Box(modifier = Modifier.height(42.dp)) {
                                PrintedKeyButton(key, false, MaterialTheme.colorScheme.secondary)
                            }
                        }
                    }
                }
                state.additionalButton.let { key ->
                    if (key.function == ToLetters) {
                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .padding(2.dp)
                        ) {
                            ToLettersButton(key) { router.onToLettersPressed() }
                        }
                    } else {
                        throw IllegalStateException("Unsupported key for numpad: $key")
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth()
            ) {
                state.matrix.forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                    ) {
                        row.forEach { key ->
                            when (key) {
                                is PrintedKey -> Box(
                                    modifier = Modifier
                                        .weight(if (key.symbol == "," || key.symbol == "." || key.symbol == "=") 0.5f else 1f)
                                        .padding(2.dp)
                                ) {
                                    val backgroundColor =
                                        if (key.symbol.isDigitsOnly()) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.secondary

                                    PrintedKeyButton(
                                        key,
                                        false,
                                        backgroundColor = backgroundColor
                                    )
                                }

                                is FunctionalKey -> {
                                    Box(
                                        modifier = Modifier
                                            .weight(if (key.function == ToSymbols) 0.5f else 1f)
                                            .padding(2.dp)
                                    ) {

                                        when (key.function) {
                                            Backspace -> BackSpaceButton(key) {
                                                service.currentInputConnection.deleteSurroundingText(1, 0)
                                            }
                                            Enter -> EnterButton(key) { service.sendKeyChar('\n') }
                                            Space -> SpaceButton(key) { service.sendKeyChar(' ') }
                                            ToSymbols -> ToSymbolsButton(key) { router.onToSymbolsPressed() }
                                            ToLetters -> ToLettersButton(key) { router.onToLettersPressed() }
                                            else -> throw IllegalStateException("Unsupported key for numpad: $key")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}