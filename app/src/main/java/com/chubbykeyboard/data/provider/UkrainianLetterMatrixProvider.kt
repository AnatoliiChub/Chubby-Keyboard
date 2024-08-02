package com.chubbykeyboard.data.provider

import com.chubbykeyboard.view.key.PrintedKey

class UkrainianLetterMatrixProvider : LetterMatrixProvider {

    override fun provide(): PrintedKeyMatrix {
        return PrintedKeyMatrix(
            firstLine = listOf(
                PrintedKey.Letter("й"),
                PrintedKey.Letter("ц"),
                PrintedKey.Letter("у"),
                PrintedKey.Letter("к"),
                PrintedKey.Letter("е", listOf("ё")),
                PrintedKey.Letter("н"),
                PrintedKey.Letter("г"),
                PrintedKey.Letter("ш"),
                PrintedKey.Letter("щ"),
                PrintedKey.Letter("з"),
                PrintedKey.Letter("х"),
                PrintedKey.Letter("ї")
            ),
            secondLine = listOf(
                PrintedKey.Letter("ф"),
                PrintedKey.Letter("і"),
                PrintedKey.Letter("в"),
                PrintedKey.Letter("а"),
                PrintedKey.Letter("п"),
                PrintedKey.Letter("р"),
                PrintedKey.Letter("о"),
                PrintedKey.Letter("л"),
                PrintedKey.Letter("д"),
                PrintedKey.Letter("ж"),
                PrintedKey.Letter("є", listOf("э")),
                PrintedKey.Letter("'", listOf("ʼ"))
            ),
            thirdLine = listOf(
                PrintedKey.Letter("я"),
                PrintedKey.Letter("ч"),
                PrintedKey.Letter("с"),
                PrintedKey.Letter("м"),
                PrintedKey.Letter("и"),
                PrintedKey.Letter("т"),
                PrintedKey.Letter("ь", listOf("ъ", "ы")),
                PrintedKey.Letter("б"),
                PrintedKey.Letter("ю"),
                PrintedKey.Letter("ґ")
            )
        )
    }
}