package com.chubbykeyboard.view.keyboard

import com.chubbykeyboard.view.key.Key
import java.util.Locale

enum class KeyboardType {
    ALPHABET,
    SYMBOL,
    NUMBER
}

data class KeyBoardParameters(
    val isCapsLockActive: Boolean,
    val currentLocal: Locale,
    val keyboardType: KeyboardType
)

data class KeyBoardState(
    val isCapsLockActive: Boolean,
    val keyMatrix : Array<Array<Key>>
)