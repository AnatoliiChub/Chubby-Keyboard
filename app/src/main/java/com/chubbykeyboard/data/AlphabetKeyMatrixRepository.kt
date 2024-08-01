package com.chubbykeyboard.data

import com.chubbykeyboard.view.key.Key
import java.util.Locale

interface AlphabetKeyMatrixRepository {

    fun getKeyMatrix(locale: Locale) : List<List<Key>>
}