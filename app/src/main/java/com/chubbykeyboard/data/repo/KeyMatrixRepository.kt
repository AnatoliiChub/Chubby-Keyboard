package com.chubbykeyboard.data.repo

import com.chubbykeyboard.keyboard.keys.Key
import com.chubbykeyboard.keyboard.KeyboardType
import java.util.Locale

interface KeyMatrixRepository {

    fun getKeyMatrix(locale: Locale, keyboardType: KeyboardType) : List<List<Key>>
}