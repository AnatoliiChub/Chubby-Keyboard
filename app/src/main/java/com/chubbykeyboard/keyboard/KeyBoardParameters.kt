package com.chubbykeyboard.keyboard

import java.util.Locale


data class KeyBoardParameters(
    val isCapsLockActive: Boolean,
    val currentLocale: Locale,
    val keyboardType: KeyboardType
)

