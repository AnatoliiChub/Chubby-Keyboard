package com.chubbykeyboard.domain

import com.chubbykeyboard.data.AlphabetKeyMatrixRepository
import com.chubbykeyboard.view.key.Key
import java.util.Locale
import javax.inject.Inject

class ProvideKeyMatrixUseCase @Inject constructor(
    private val repository: AlphabetKeyMatrixRepository,
) {

    fun provide(locale: Locale): List<List<Key>> {
        return repository.getKeyMatrix(locale)
    }
}