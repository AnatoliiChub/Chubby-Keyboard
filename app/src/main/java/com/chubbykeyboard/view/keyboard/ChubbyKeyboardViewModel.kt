package com.chubbykeyboard.view.keyboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chubbykeyboard.view.key.FunctionalKey
import com.chubbykeyboard.view.key.Key
import com.chubbykeyboard.view.key.PrintedKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class ChubbyKeyboardViewModel @Inject constructor() : ViewModel() {

    companion object {
        private val DEFAULT_ALPHABET_KEY_MATRIX: Array<Array<Key>> = arrayOf(
            arrayOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p").map { PrintedKey.Letter(it) }.toTypedArray(),
            arrayOf("a", "s", "d", "f", "g", "h", "j", "k", "l").map { PrintedKey.Letter(it) }.toTypedArray(),
            arrayOf(
                FunctionalKey.CapsLock,
                PrintedKey.Letter("z"),
                PrintedKey.Letter("x"),
                PrintedKey.Letter("c"),
                PrintedKey.Letter("v"),
                PrintedKey.Letter("b"),
                PrintedKey.Letter("n"),
                PrintedKey.Letter("m"),
                FunctionalKey.Backspace
            ),
            arrayOf(
                FunctionalKey.ToSymbols,
                PrintedKey.Symbol(","),
                FunctionalKey.SwitchLanguage,
                FunctionalKey.Space,
                PrintedKey.Symbol("."),
                FunctionalKey.Enter
            )
        )

        private val DEFAULT_STATE = KeyBoardState(false, DEFAULT_ALPHABET_KEY_MATRIX)
    }

    private val _uiState = MutableStateFlow(
        KeyBoardParameters(false, Locale.getDefault(), KeyboardType.ALPHABET)
    )

    val uiState: StateFlow<KeyBoardState> = _uiState
        .map {
            val keyMatrix = when (it.keyboardType) {
                KeyboardType.ALPHABET -> {
                    DEFAULT_ALPHABET_KEY_MATRIX
                }
                KeyboardType.SYMBOL -> TODO()
                KeyboardType.NUMBER -> TODO()
            }
            KeyBoardState(it.isCapsLockActive, keyMatrix)
        }.stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = DEFAULT_STATE
        )

    fun onCapsLockPressed() {
        _uiState.value = _uiState.value.copy(isCapsLockActive = !_uiState.value.isCapsLockActive)
    }
}