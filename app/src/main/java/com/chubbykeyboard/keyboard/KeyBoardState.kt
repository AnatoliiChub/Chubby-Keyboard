package com.chubbykeyboard.keyboard

import com.chubbykeyboard.data.parser.KeyMatrix

sealed class KeyBoardState {

    data object Loading : KeyBoardState()

    data class Content(
        val isCapsLockActive: Boolean,
        val keyMatrix: KeyMatrix
    ) : KeyBoardState()
}