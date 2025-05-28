package com.chubbykeyboard.ui.view.keyboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chubbykeyboard.data.repo.AccessibilitySettingsRepositoryImpl
import com.chubbykeyboard.domain.GetCurrentSupportedLocaleUseCase
import com.chubbykeyboard.domain.ProvideKeyMatrixUseCase
import com.chubbykeyboard.domain.SwitchLanguageUseCase
import com.chubbykeyboard.domain.keyboard.KeyBoardParameters
import com.chubbykeyboard.domain.keyboard.KeyBoardState
import com.chubbykeyboard.domain.keyboard.KeyboardType
import com.chubbykeyboard.service.HapticManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Locale

class ChubbyKeyboardViewModel(
    private val provideKeyMatrixUseCase: ProvideKeyMatrixUseCase,
    private val switchLanguageUseCase: SwitchLanguageUseCase,
    private val getCurrentSupportedLocaleUseCase: GetCurrentSupportedLocaleUseCase,
    private val settingsRepo: AccessibilitySettingsRepositoryImpl,
    private val hapticManager: HapticManager
) : ViewModel() {

    private val workDispatcher = Dispatchers.Default
    private val isCapsLockActive = MutableStateFlow(false)
    private val currentLocale = MutableStateFlow(Locale.getDefault())
    private val keyboardType = MutableStateFlow(KeyboardType.LETTERS)

    private val _uiState =
        combine(
            isCapsLockActive,
            currentLocale,
            keyboardType,
            settingsRepo.getDebounce()
        ) { capsLock, locale, keyboardType, debounce ->
            KeyBoardParameters(capsLock, locale, keyboardType, debounce)
        }

    val router: FunctionalRouter = FunctionalRouter(
        onCapsLockPressed = this::onCapsLockPressed,
        onSwitchLangPressed = this::onSwitchLanguagePressed,
        onToSymbolsPressed = this::onToSymbolsPressed,
        onToLettersPressed = this::onToLettersPressed,
        onToAdditionalSymbolsPressed = this::onToAdditionalSymbolsPressed,
        onToNumPadPressed = this::onToNumPadPressed
    )

    val uiState: StateFlow<KeyBoardState> = _uiState
        .map {
            val keyMatrix = provideKeyMatrixUseCase.provide(it.currentLocale, it.keyboardType)

            KeyBoardState.Content(it.isCapsLockActive, keyMatrix, it.debounce)
        }.flowOn(workDispatcher)
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(500),
            initialValue = KeyBoardState.Loading
        )

    init {
        viewModelScope.launch(workDispatcher) {
            currentLocale.value = getCurrentSupportedLocaleUseCase.invoke()
        }
    }

    fun keyEventHaptic() {
        hapticManager.keyEvent()
    }

    private fun onToSymbolsPressed() {
        hapticManager.functionalEvent()
        keyboardType.value = KeyboardType.SYMBOLS
    }

    private fun onToLettersPressed() {
        hapticManager.functionalEvent()
        keyboardType.value = KeyboardType.LETTERS
    }

    private fun onCapsLockPressed() {
        hapticManager.functionalEvent()
        isCapsLockActive.value = !isCapsLockActive.value
    }

    private fun onToAdditionalSymbolsPressed() {
        hapticManager.functionalEvent()
        keyboardType.value = KeyboardType.ADDITIONAL_SYMBOLS
    }

    private fun onToNumPadPressed() {
        hapticManager.functionalEvent()
        keyboardType.value = KeyboardType.NUMPAD
    }

    private fun onSwitchLanguagePressed() {
        hapticManager.functionalEvent()
        viewModelScope.launch(workDispatcher) {
            currentLocale.value = switchLanguageUseCase.switchToNextLanguage(currentLocale.value)
        }
    }
}
