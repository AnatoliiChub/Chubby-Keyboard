package com.chubbykeyboard.data.repo

import androidx.collection.LruCache
import com.chubbykeyboard.data.SupportedLanguages
import com.chubbykeyboard.data.parser.JsonKeyMatrixParser
import com.chubbykeyboard.data.parser.KeyMatrix
import com.chubbykeyboard.domain.keyboard.KeyboardType
import java.util.Locale
import javax.inject.Inject

class CachedKeyMatrixRepository @Inject constructor(
    private val jsonKeyMatrixParser: JsonKeyMatrixParser
) : KeyMatrixRepository {

    companion object {
        // 4 locales with 4 key matrices each
        private const val DEFAULT_CACHE_SIZE = 16
        const val DEFAULT_LETTER = "en/letters.json"
        const val DEFAULT_SYMBOLS = "en/symbols.json"
        const val DEFAULT_ADDITIONAL_SYMBOLS = "en/additional_symbols.json"
        const val DEFAULT_NUMPAD = "en/numpad.json"
    }

    private val cachedKeyMatrix = LruCache<String, KeyMatrix>(DEFAULT_CACHE_SIZE)

    override fun getKeyMatrix(locale: Locale, keyboardType: KeyboardType): KeyMatrix {
        val key = "$locale$keyboardType"
        cachedKeyMatrix[key]?.let {
            return it
        }
        val language = SupportedLanguages.fromCode(locale.language)
        val file = findFile(keyboardType, language)

        val keyMatrix = jsonKeyMatrixParser.parse(file)
        cachedKeyMatrix.put(key, keyMatrix)

        return keyMatrix
    }

    private fun findFile(
        keyboardType: KeyboardType,
        language: SupportedLanguages?
    ) = when (keyboardType) {
        KeyboardType.LETTERS -> language?.lettersFile ?: DEFAULT_LETTER
        KeyboardType.SYMBOLS -> language?.symbolsFile ?: DEFAULT_SYMBOLS
        KeyboardType.ADDITIONAL_SYMBOLS -> language?.additionalSymbolsFile ?: DEFAULT_ADDITIONAL_SYMBOLS
        KeyboardType.NUMPAD -> language?.numpadFile ?: DEFAULT_NUMPAD
    }
}
