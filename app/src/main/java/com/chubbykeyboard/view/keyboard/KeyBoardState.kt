package com.chubbykeyboard.view.keyboard

import java.util.Locale

enum class KeyboardType {
    ALPHABET,
    SYMBOL,
    NUMBER
}

data class KeyBoardState(
    val isCapsLockActive: Boolean,
    val currentLocal: Locale,
    val keyboardType: KeyboardType
)