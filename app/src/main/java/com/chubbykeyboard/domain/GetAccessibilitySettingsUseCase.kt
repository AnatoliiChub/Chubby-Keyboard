package com.chubbykeyboard.domain

import com.chubbykeyboard.R
import com.chubbykeyboard.data.repo.AccessibilitySettingsRepositoryImpl
import com.chubbykeyboard.data.repo.StringsRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAccessibilitySettingsUseCase @Inject constructor(
    private val settingsRepo: AccessibilitySettingsRepositoryImpl,
    private val stringsRepo: StringsRepository
) {

    fun invoke() = settingsRepo.getDebounce().map { value ->
        Settings.RangedSettings(
            title = stringsRepo.getString(R.string.settings_debounce),
            value = value,
            range = 0L..1000L,
            description = ""
        )
    }.map { listOf(it) }
}
