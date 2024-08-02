package com.chubbykeyboard.data

import com.chubbykeyboard.view.key.Key
import com.chubbykeyboard.view.keyboard.KeyboardType
import java.util.Locale

interface KeyMatrixRepository {

    fun getKeyMatrix(locale: Locale, keyboardType: KeyboardType) : List<List<Key>>
}