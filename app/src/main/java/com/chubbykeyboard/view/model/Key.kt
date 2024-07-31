package com.chubbykeyboard.view.model

sealed class Key {
    abstract val displayedSymbol: String
}

sealed class FunctionalKey : Key() {
    data object Shift : Key() {
        private var isShiftedParam: Boolean = false

        fun updateShift(isShifted: Boolean) {
            isShiftedParam = isShifted
        }

        override val displayedSymbol: String
            get() = if (isShiftedParam) "\u21EA" else "\u21E7"
    }

    data object ToNumber : Key() {
        override val displayedSymbol: String
            get() = "?123"
    }


    data object Backspace : PrintedKey() {
        override val displayedSymbol: String
            get() = "⌫"
    }

    data object Enter : Key() {
        override val displayedSymbol: String
            get() = "\u23CE"
    }
}

sealed class PrintedKey : Key() {
    open val printedSymbol: String
        get() = displayedSymbol

    data class Letter(val letter: String) : PrintedKey() {

        private var isCapital: Boolean = false

        fun setCapital(isCapital: Boolean) {
            this.isCapital = isCapital
        }

        override val displayedSymbol: String
            get() = if (isCapital) letter.uppercase() else letter
    }


    data object Space : PrintedKey() {
        override val displayedSymbol: String
            get() = "\\_____/"
        override val printedSymbol: String
            get() = " "
    }

}