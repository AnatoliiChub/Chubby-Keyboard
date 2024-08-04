package com.chubbykeyboard.data.repo

import com.chubbykeyboard.data.parser.KeyMatrix
import com.chubbykeyboard.keyboard.KeyboardType
import java.util.Locale

interface KeyMatrixRepository {

    fun getKeyMatrix(locale: Locale, keyboardType: KeyboardType): KeyMatrix
}