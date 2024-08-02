package com.chubbykeyboard.data

import androidx.collection.LruCache
import com.chubbykeyboard.view.key.Key
import java.util.Locale
import javax.inject.Inject

class CachedAlphabetKeyMatrixRepository @Inject constructor(
    private val jsonKeyMatrixParser: JsonKeyMatrixParser
) : AlphabetKeyMatrixRepository {

    companion object {
        private const val DEFAULT_CACHE_SIZE = 4
        const val DEFAULT_FILE = "en/alphabet.json"
    }

    private val cachedKeyMatrix = LruCache<Locale, List<List<Key>>>(DEFAULT_CACHE_SIZE)

    override fun getKeyMatrix(locale: Locale): List<List<Key>> {
        cachedKeyMatrix[locale]?.let {
            return it
        }
        val language = SupportedLanguages.fromCode(locale.language)
        val keyMatrix = jsonKeyMatrixParser.parse(language?.keyFile ?: DEFAULT_FILE)
        cachedKeyMatrix.put(locale, keyMatrix)
        return keyMatrix
    }

}


