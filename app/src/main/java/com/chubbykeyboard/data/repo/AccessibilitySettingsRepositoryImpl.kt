package com.chubbykeyboard.data.repo

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AccessibilitySettingsRepositoryImpl @Inject constructor(@ApplicationContext val context: Context) : AccessibilitySettingsRepository {

    companion object {
        private const val DEBOUNCE_FIELD = "settings_debounce"
        private const val DEFAULT_DEBOUNCE_VALUE = 0L
        private val Context.dataStore by preferencesDataStore(name = "settings_prefs")
        private val DEBOUNCE_KEY = longPreferencesKey(DEBOUNCE_FIELD)
    }

    override suspend fun setDebounce(value: Long) {
        context.dataStore.edit { settings ->
            settings[DEBOUNCE_KEY] = value
        }
    }

    override fun getDebounce(): Flow<Long> {
        return context.dataStore.data.map { preferences ->
            preferences[DEBOUNCE_KEY] ?: DEFAULT_DEBOUNCE_VALUE
        }
    }
}
