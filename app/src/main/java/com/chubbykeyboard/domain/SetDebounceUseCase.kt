package com.chubbykeyboard.domain

import com.chubbykeyboard.data.repo.AccessibilitySettingsRepositoryImpl
import javax.inject.Inject

class SetDebounceUseCase @Inject constructor(private val settingsRepo: AccessibilitySettingsRepositoryImpl) {

    suspend fun invoke(value: Long) = settingsRepo.setDebounce(value)
}
