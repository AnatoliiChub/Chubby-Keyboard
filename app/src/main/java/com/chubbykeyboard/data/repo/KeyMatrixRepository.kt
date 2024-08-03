package com.chubbykeyboard.data.repo

import com.chubbykeyboard.Key
import com.chubbykeyboard.ui.state.KeyboardType
import java.util.Locale

interface KeyMatrixRepository {

    fun getKeyMatrix(locale: Locale, keyboardType: KeyboardType) : List<List<Key>>
}