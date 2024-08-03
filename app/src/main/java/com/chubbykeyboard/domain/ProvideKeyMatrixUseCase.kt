package com.chubbykeyboard.domain

import com.chubbykeyboard.data.repo.KeyMatrixRepository
import com.chubbykeyboard.view.key.Key
import com.chubbykeyboard.view.keyboard.KeyboardType
import java.util.Locale
import javax.inject.Inject

class ProvideKeyMatrixUseCase @Inject constructor(
    private val repository: KeyMatrixRepository,
) {

    fun provide(locale: Locale, keyboardType: KeyboardType): List<List<Key>> {
        return repository.getKeyMatrix(locale, keyboardType)
    }
}