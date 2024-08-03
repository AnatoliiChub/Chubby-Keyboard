package com.chubbykeyboard.ui.state

import com.chubbykeyboard.Key

sealed class KeyBoardState {

    data object Loading : KeyBoardState()

    data class Content(
        val isCapsLockActive: Boolean,
        val keyMatrix: List<List<Key>>
    ) : KeyBoardState()
}