package com.chubbykeyboard.data.provider

class EnglishLetterMatrixProvider : LetterMatrixProvider {

    override fun provide(): LetterKeyMatrix {
        return LetterKeyMatrix(
            firstLine = listOf("q", "w", "e", "r", "t", "y", "u", "i", "o", "p"),
            secondLine = listOf("a", "s", "d", "f", "g", "h", "j", "k", "l"),
            thirdLine = listOf("z", "x", "c", "v", "b", "n", "m")
        )
    }
}