package com.chubbykeyboard.data.parser

import com.chubbykeyboard.Key

interface KeyMatrixParser {

    fun parse(fileName : String) : List<List<Key>>
}