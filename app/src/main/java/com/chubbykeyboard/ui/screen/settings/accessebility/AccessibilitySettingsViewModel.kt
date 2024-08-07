package com.chubbykeyboard.ui.screen.settings.accessebility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chubbykeyboard.domain.GetAccessibilitySettingsUseCase
import com.chubbykeyboard.domain.SetDebounceUseCase
import com.chubbykeyboard.domain.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccessibilitySettingsViewModel @Inject constructor(
    settingsUseCase: GetAccessibilitySettingsUseCase,
    private val setDebounceUseCase: SetDebounceUseCase
) : ViewModel() {

    val uiState: StateFlow<List<Settings>> = settingsUseCase.invoke()
        .flowOn(Dispatchers.IO)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            emptyList()
        )

    fun onDebounceChanged(newValue: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            setDebounceUseCase.invoke(newValue)
        }
    }
}