package com.chubbykeyboard.ui.view.keyboard

class FunctionalRouter(
    val onCapsLockPressed: () -> Unit,
    val onSwitchLangPressed: () -> Unit,
    val onToSymbolsPressed: () -> Unit,
    val onToLettersPressed: () -> Unit,
    val onToAdditionalSymbolsPressed: () -> Unit,
    val onToNumPadPressed: () -> Unit
)
