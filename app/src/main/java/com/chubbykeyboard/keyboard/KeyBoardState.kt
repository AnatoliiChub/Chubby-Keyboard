package com.chubbykeyboard.keyboard

import com.chubbykeyboard.keyboard.keys.Key

sealed class KeyBoardState {

    data object Loading : KeyBoardState()

    data class Content(
        val isCapsLockActive: Boolean,
        val keyMatrix: List<List<Key>>
    ) : KeyBoardState()
}