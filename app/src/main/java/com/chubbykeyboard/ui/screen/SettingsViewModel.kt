package com.chubbykeyboard.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chubbykeyboard.data.repo.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(val repo: SettingsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(emptyList<Settings>())
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        emptyList()
    )

    init {
        fetchSettings()
    }

    fun onDebounceChanged(newValue: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.setDebounce(newValue)
            fetchSettings()
        }
    }

    private fun fetchSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = repo.getAllSettings()
        }
    }
}