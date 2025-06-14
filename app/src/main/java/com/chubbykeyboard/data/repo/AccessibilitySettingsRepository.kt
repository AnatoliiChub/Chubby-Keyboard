package com.chubbykeyboard.data.repo

import kotlinx.coroutines.flow.Flow

interface AccessibilitySettingsRepository {
    suspend fun setDebounce(value: Long)
    fun getDebounce(): Flow<Long>
}
