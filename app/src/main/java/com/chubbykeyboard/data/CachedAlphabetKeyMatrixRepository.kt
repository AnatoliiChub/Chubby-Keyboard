package com.chubbykeyboard.data

import androidx.collection.LruCache
import com.chubbykeyboard.data.provider.EnglishLetterMatrixProvider
import com.chubbykeyboard.data.provider.PrintedKeyMatrix
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
        //todo MOVE IT TO Json file or each language
        val letterKeyMatrix = LETTER_PROVIDERS[language]?.provide() ?: DEFAULT_PROVIDER.provide()
        val keyMatrix = createTemplatedKeyMatrix(letterKeyMatrix)
        cachedKeyMatrix.put(locale, keyMatrix)
        return keyMatrix
    }

    private fun createTemplatedKeyMatrix(
        letterKeyMatrix: PrintedKeyMatrix
    ) = with(letterKeyMatrix) {
        listOf(
            firstLine as List<Key>,
            secondLine as List<Key>,
            listOf(
                FunctionalKey.CapsLock,
                *thirdLine.toTypedArray(),
                FunctionalKey.Backspace
            ),
            listOf(
                FunctionalKey.ToSymbols,
                PrintedKey.Symbol(","),
                FunctionalKey.SwitchLanguage,
                FunctionalKey.Space,
                PrintedKey.Symbol(".", "&%+\"-:'@;/()#!,?".map { it.toString() }),
                FunctionalKey.Enter
            )
        )
    }

}


