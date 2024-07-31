package com.chubbykeyboard.view.keyboard

import androidx.lifecycle.ViewModel
import com.chubbykeyboard.view.model.FunctionalKey
import com.chubbykeyboard.view.model.Key
import com.chubbykeyboard.view.model.PrintedKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ChubbyKeyboardViewModel @Inject constructor() : ViewModel() {

    private val isShifted: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val currentKeyGrid: StateFlow<Array<Array<Key>>> = MutableStateFlow(
        arrayOf(
            arrayOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p").map { PrintedKey.Letter(it) }.toTypedArray(),
            arrayOf("a", "s", "d", "f", "g", "h", "j", "k", "l").map { PrintedKey.Letter(it) }.toTypedArray(),
            arrayOf(
                FunctionalKey.Shift,
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
                FunctionalKey.ToNumber,
                PrintedKey.Letter(","),
                PrintedKey.Space,
                PrintedKey.Letter("."),
                FunctionalKey.Enter
            )
        )
    )

    fun onShiftPressed() : Boolean  {
        isShifted.value = !isShifted.value
        return isShifted.value
    }
}