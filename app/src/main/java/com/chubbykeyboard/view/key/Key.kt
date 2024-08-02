package com.chubbykeyboard.view.key

sealed class Key {
    abstract val displayedSymbol: String
}

sealed class FunctionalKey : Key() {
    data object CapsLock : FunctionalKey() {
        private var isShiftedParam: Boolean = false

        fun updateShift(isShifted: Boolean) {
            isShiftedParam = isShifted
        }

        override val displayedSymbol: String
            get() = if (isShiftedParam) "\u21EA" else "\u21E7"
    }

    data object ToSymbols : FunctionalKey() {
        override val displayedSymbol: String
            get() = "?123"
    }


    data object Backspace : FunctionalKey() {
        override val displayedSymbol: String
            get() = "⌫"
    }

    data object Enter : FunctionalKey() {
        override val displayedSymbol: String
            get() = "\u23CE"
    }

    data object SwitchLanguage : FunctionalKey() {
        override val displayedSymbol: String
            get() = "\uD83C\uDF10\uFE0E"
    }

    data object Space : FunctionalKey() {
        override val displayedSymbol: String
            get() = "\u2423"
    }
}

sealed class PrintedKey(val alternatives: List<String>) : Key() {
    open val printedSymbol: String
        get() = displayedSymbol


    class Symbol(private val symbol: String, alternatives: List<String> = emptyList()) : PrintedKey(alternatives) {
        override val displayedSymbol: String
            get() = symbol
    }

    class Letter(private val letter: String, alternatives: List<String> = emptyList()) : PrintedKey(alternatives) {

        private var isCapital: Boolean = false

        fun setCapital(isCapital: Boolean) {
            this.isCapital = isCapital
        }

        override val displayedSymbol: String
            get() = if (isCapital) letter.uppercase() else letter
    }
}