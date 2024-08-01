package com.chubbykeyboard.domain

import com.chubbykeyboard.data.LocalesRepository
import com.chubbykeyboard.data.SupportedLanguages
import java.util.Locale
import javax.inject.Inject

class SwitchLanguageUseCase @Inject constructor(
    private val localesRepository: LocalesRepository,
) {

    fun switchToNextLanguage(locale: Locale): Locale {
        val availableLocales =
            localesRepository.getLocales().filter { SupportedLanguages.fromCode(it.language) != null }
        var currentIndex = availableLocales.indexOf(locale)
        if (availableLocales.isEmpty()) {
            return locale
        }
        currentIndex++
        if (currentIndex >= availableLocales.size) {
            currentIndex = 0
        }
        val newLocale = availableLocales[currentIndex]
        return newLocale
    }
}