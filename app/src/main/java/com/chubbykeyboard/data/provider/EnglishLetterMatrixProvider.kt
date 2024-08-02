package com.chubbykeyboard.data.provider

import com.chubbykeyboard.view.key.PrintedKey

class EnglishLetterMatrixProvider : LetterMatrixProvider {

    override fun provide(): PrintedKeyMatrix {
        return PrintedKeyMatrix(
            firstLine = listOf(
                PrintedKey.Letter("q"),
                PrintedKey.Letter("w"),
                PrintedKey.Letter("e", "èēêëé".map { it.toString() }),
                PrintedKey.Letter("r"),
                PrintedKey.Letter("t"),
                PrintedKey.Letter("y"),
                PrintedKey.Letter("u", "ûūüùú".map { it.toString() }),
                PrintedKey.Letter("i", "îìïīí".map { it.toString() }),
                PrintedKey.Letter("o", "õōøœòöôó".map { it.toString() }),
                PrintedKey.Letter("p")
            ),
            secondLine = listOf(
                PrintedKey.Letter("a", "àáâäæãåā".map { it.toString() }),
                PrintedKey.Letter("s", "ß".map { it.toString() }),
                PrintedKey.Letter("d"),
                PrintedKey.Letter("f"),
                PrintedKey.Letter("g"),
                PrintedKey.Letter("h"),
                PrintedKey.Letter("j"),
                PrintedKey.Letter("k"),
                PrintedKey.Letter("l")
            ),
            thirdLine = listOf(
                PrintedKey.Letter("z"),
                PrintedKey.Letter("x"),
                PrintedKey.Letter("c", "ç".map { it.toString() }),
                PrintedKey.Letter("v"),
                PrintedKey.Letter("b"),
                PrintedKey.Letter("n", "ñ".map { it.toString() }),
                PrintedKey.Letter("m")
            )
        )
    }
}