package com.chubbykeyboard.data.parser

interface KeyMatrixParser {

    fun parse(fileName: String): KeyMatrix
}