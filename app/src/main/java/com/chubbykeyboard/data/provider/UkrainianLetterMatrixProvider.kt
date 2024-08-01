package com.chubbykeyboard.data.provider

class UkrainianLetterMatrixProvider : LetterMatrixProvider {

    override fun provide(): LetterKeyMatrix {
        return LetterKeyMatrix(
            firstLine = listOf("й", "ц", "у", "к", "е", "н", "г", "ш", "щ", "з", "х", "ї"),
            secondLine = listOf("ф", "і", "в", "а", "п", "р", "о", "л", "д", "ж", "є", "'"),
            thirdLine = listOf("я", "ч", "с", "м", "и", "т", "ь", "б", "ю", "ґ")
        )
    }
}