package com.chubbykeyboard.view.keyboard

import com.chubbykeyboard.view.key.Key
import java.util.Locale

enum class KeyboardType {
    LETTERS,
    SYMBOLS,
    ADDITIONAL_SYMBOLS,
    NUMBER
}

data class KeyBoardParameters(
    val isCapsLockActive: Boolean,
    val currentLocale: Locale,
    val keyboardType: KeyboardType
)

sealed class KeyBoardState {

    data object Loading : KeyBoardState()

    data class Content(
        val isCapsLockActive: Boolean,
        val keyMatrix: List<List<Key>>
    ) : KeyBoardState()
}