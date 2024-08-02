package com.chubbykeyboard.data.provider

import com.chubbykeyboard.view.key.PrintedKey

data class PrintedKeyMatrix(
    val firstLine: List<PrintedKey>,
    val secondLine: List<PrintedKey>,
    val thirdLine: List<PrintedKey>
)