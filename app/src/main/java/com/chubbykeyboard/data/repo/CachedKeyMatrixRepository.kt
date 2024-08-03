package com.chubbykeyboard.data.repo

import androidx.collection.LruCache
import com.chubbykeyboard.data.SupportedLanguages
import com.chubbykeyboard.data.parser.JsonKeyMatrixParser
import com.chubbykeyboard.view.key.Key
import com.chubbykeyboard.view.keyboard.KeyboardType
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
    }

    private val cachedKeyMatrix = LruCache<String, List<List<Key>>>(DEFAULT_CACHE_SIZE)

    override fun getKeyMatrix(locale: Locale, keyboardType: KeyboardType): List<List<Key>> {
        val key = "$locale$keyboardType"
        cachedKeyMatrix[key]?.let {
            return it
        }
        val language = SupportedLanguages.fromCode(locale.language)

        val file = when (keyboardType) {
            KeyboardType.LETTERS -> language?.lettersFile ?: DEFAULT_LETTER
            KeyboardType.SYMBOLS -> language?.symbolsFile ?: DEFAULT_SYMBOLS
            //TODO: add more keyboard types
            else -> ""
        }
        val keyMatrix = jsonKeyMatrixParser.parse(file)
        cachedKeyMatrix.put(key, keyMatrix)
        return keyMatrix
    }
}
