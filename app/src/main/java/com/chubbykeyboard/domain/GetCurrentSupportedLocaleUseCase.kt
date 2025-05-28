package com.chubbykeyboard.domain

import com.chubbykeyboard.data.repo.LocalesRepository
import com.chubbykeyboard.data.SupportedLanguages
import java.util.Locale
import javax.inject.Inject

class GetCurrentSupportedLocaleUseCase @Inject constructor(val repo: LocalesRepository) {

    fun invoke() = repo.getLocales().firstOrNull {
        SupportedLanguages.fromCode(it.language) != null
    } ?: Locale(SupportedLanguages.ENGLISH.code)
}
