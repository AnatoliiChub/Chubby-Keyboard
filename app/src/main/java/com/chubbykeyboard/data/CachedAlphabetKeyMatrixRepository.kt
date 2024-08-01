package com.chubbykeyboard.data

import androidx.collection.LruCache
import com.chubbykeyboard.data.provider.EnglishLetterMatrixProvider
import com.chubbykeyboard.data.provider.LetterKeyMatrix
import com.chubbykeyboard.data.provider.UkrainianLetterMatrixProvider
import com.chubbykeyboard.view.key.FunctionalKey
import com.chubbykeyboard.view.key.Key
import com.chubbykeyboard.view.key.PrintedKey
import java.util.Locale
import javax.inject.Inject

class CachedAlphabetKeyMatrixRepository @Inject constructor() : AlphabetKeyMatrixRepository {
    companion object {

        private const val DEFAULT_CACHE_SIZE = 4

        val LETTER_PROVIDERS = mapOf(
            // TODO: rewrite to factory
            SupportedLanguages.ENGLISH to EnglishLetterMatrixProvider(),
            SupportedLanguages.UKRAINIAN to UkrainianLetterMatrixProvider()
        )

        val DEFAULT_PROVIDER = EnglishLetterMatrixProvider()
    }

    private val cachedKeyMatrix = LruCache<Locale, List<List<Key>>>(DEFAULT_CACHE_SIZE)

    override fun getKeyMatrix(locale: Locale): List<List<Key>> {
        cachedKeyMatrix[locale]?.let {
            return it
        }
        val language = SupportedLanguages.fromCode(locale.language)
        val letterKeyMatrix = LETTER_PROVIDERS[language]?.provide() ?: DEFAULT_PROVIDER.provide()
        val keyMatrix = createTemplatedKeyMatrix(letterKeyMatrix)
        cachedKeyMatrix.put(locale, keyMatrix)
        return keyMatrix
    }

    private fun createTemplatedKeyMatrix(
        letterKeyMatrix: LetterKeyMatrix
    ) = with(letterKeyMatrix) {
        listOf(
            firstLine.map { PrintedKey.Letter(it) },
            secondLine.map { PrintedKey.Letter(it) },
            listOf(
                FunctionalKey.CapsLock,
                *thirdLine.map { PrintedKey.Letter(it) }.toTypedArray(),
                FunctionalKey.Backspace
            ),
            listOf(
                FunctionalKey.ToSymbols,
                PrintedKey.Symbol(","),
                FunctionalKey.SwitchLanguage,
                FunctionalKey.Space,
                PrintedKey.Symbol("."),
                FunctionalKey.Enter
            )
        )
    }

}


