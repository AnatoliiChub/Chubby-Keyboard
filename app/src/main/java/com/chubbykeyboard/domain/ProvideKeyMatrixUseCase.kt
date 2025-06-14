package com.chubbykeyboard.domain

import com.chubbykeyboard.data.repo.KeyMatrixRepository
import com.chubbykeyboard.domain.keyboard.KeyboardType
import java.util.Locale
import javax.inject.Inject

class ProvideKeyMatrixUseCase @Inject constructor(
    private val repository: KeyMatrixRepository,
) {
    fun provide(locale: Locale, keyboardType: KeyboardType) = repository.getKeyMatrix(locale, keyboardType)
}
