package com.chubbykeyboard.data

import com.chubbykeyboard.view.key.Key

interface KeyMatrixParser {

    fun parse(fileName : String) : List<List<Key>>
}