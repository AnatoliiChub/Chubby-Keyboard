package com.chubbykeyboard.ui.view.keyboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chubbykeyboard.domain.GetCurrentSupportedLocaleUseCase
import com.chubbykeyboard.domain.ProvideKeyMatrixUseCase
import com.chubbykeyboard.domain.SwitchLanguageUseCase
import com.chubbykeyboard.keyboard.KeyBoardParameters
import com.chubbykeyboard.keyboard.KeyBoardState
import com.chubbykeyboard.keyboard.KeyboardType
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
    private val getCurrentSupportedLocaleUseCase: GetCurrentSupportedLocaleUseCase
) : ViewModel() {

    private val workDispatcher = Dispatchers.Default
    private val isCapsLockActive = MutableStateFlow(false)
    private val currentLocale = MutableStateFlow(Locale.getDefault())
    private val keyboardType = MutableStateFlow(KeyboardType.LETTERS)
    private val _uiState =
        combine(isCapsLockActive, currentLocale, keyboardType) { capsLock, locale, keyboardType ->
            KeyBoardParameters(capsLock, locale, keyboardType)
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
            KeyBoardState.Content(it.isCapsLockActive, keyMatrix)
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

    private fun onToSymbolsPressed() {
        keyboardType.value = KeyboardType.SYMBOLS
    }

    private fun onToLettersPressed() {
        keyboardType.value = KeyboardType.LETTERS
    }

    private fun onCapsLockPressed() {
        isCapsLockActive.value = !isCapsLockActive.value
    }

    private fun onToAdditionalSymbolsPressed() {
        keyboardType.value = KeyboardType.ADDITIONAL_SYMBOLS
    }

    private fun onToNumPadPressed() {
        keyboardType.value = KeyboardType.NUMPAD
    }

    private fun onSwitchLanguagePressed() {
        viewModelScope.launch(workDispatcher) {
            currentLocale.value = switchLanguageUseCase.switchToNextLanguage(currentLocale.value)
        }
    }
}