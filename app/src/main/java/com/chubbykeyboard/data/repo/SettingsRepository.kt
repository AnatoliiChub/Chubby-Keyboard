package com.chubbykeyboard.data.repo

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.chubbykeyboard.R
import com.chubbykeyboard.ui.screen.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

//TODO migrate to datastore https://developer.android.com/codelabs/android-preferences-datastore#0
//TODO REFACTOR IT
class SettingsRepository @Inject constructor(@ApplicationContext val context: Context) {

    companion object {
        private const val DEBOUNCE_FIELD = "debounce"
        private const val DEFAULT_DEBOUNCE_VALUE = 200L
    }

    private val prefs: SharedPreferences = context.getSharedPreferences("settings_prefs", Context.MODE_PRIVATE)

    suspend fun getAllSettings(): List<Settings> {
        return listOf(
            getSettings(DEBOUNCE_FIELD)
        )
    }

    private suspend fun getSettings(settings: String): Settings {

        return when (settings) {
            DEBOUNCE_FIELD -> {
                val debounceValue = prefs.getLong(DEBOUNCE_FIELD, DEFAULT_DEBOUNCE_VALUE)
                Log.d("get", "getSettings:  $settings : $debounceValue")
                return Settings.RangedSettings(
                    title = context.resources.getString(R.string.settings_debounce),
                    description = context.resources.getString(R.string.debounce_description),
                    min = 0,
                    max = 1000,
                    value = debounceValue
                )
            }

            else -> throw IllegalArgumentException("Unknown settings")

        }
    }

    suspend fun setDebounce(value: Long) {
        setSettings(DEBOUNCE_FIELD, value)
    }

    suspend fun getDebounce(): Long {
        return prefs.getLong(DEBOUNCE_FIELD, DEFAULT_DEBOUNCE_VALUE)
    }

    private suspend fun setSettings(field: String, value: Long) {
        Log.d("set", "setSettings:  $field  : $value")
        when (field) {
            DEBOUNCE_FIELD -> {
                prefs.edit().putLong(DEBOUNCE_FIELD, value).commit()
            }

            else -> throw IllegalArgumentException("Unknown settings")
        }
    }
}