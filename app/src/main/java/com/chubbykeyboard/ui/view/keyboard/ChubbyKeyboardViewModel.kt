package com.chubbykeyboard.ui.view.keyboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chubbykeyboard.domain.GetCurrentSupportedLocaleUseCase
import com.chubbykeyboard.domain.ProvideKeyMatrixUseCase
import com.chubbykeyboard.domain.SwitchLanguageUseCase
import com.chubbykeyboard.ui.state.KeyBoardParameters
import com.chubbykeyboard.ui.state.KeyBoardState
import com.chubbykeyboard.ui.state.KeyboardType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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

    init {
        viewModelScope.launch(Dispatchers.IO) {
            currentLocale.value = getCurrentSupportedLocaleUseCase.invoke()
        }
    }

    private val _uiState =
        combine(isCapsLockActive, currentLocale, keyboardType) { capsLock, locale, keyboardType ->
            KeyBoardParameters(capsLock, locale, keyboardType)
        }


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

    fun onToSymbolsPressed() {
        keyboardType.value = KeyboardType.SYMBOLS
    }

    fun onToLettersPressed() {
        keyboardType.value = KeyboardType.LETTERS
    }

    fun onCapsLockPressed() {
        isCapsLockActive.value = !isCapsLockActive.value
    }

    fun switchLanguage() {
        currentLocale.value = switchLanguageUseCase.switchToNextLanguage(currentLocale.value)
    }
}