package com.chubbykeyboard.ui.screen.settings.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chubbykeyboard.ui.screen.settings.SettingsScreens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class SettingsViewModel @Inject constructor() : ViewModel() {

    companion object {
        private val SETTINGS_LIST = listOf(
            SettingsScreens.ACCESSIBILITY,
            SettingsScreens.DEBUG,
            SettingsScreens.ABOUT,
        )
    }

    val uiState = MutableStateFlow(SETTINGS_LIST).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 500),
        SETTINGS_LIST
    )
}
