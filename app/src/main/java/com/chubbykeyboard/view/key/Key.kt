package com.chubbykeyboard.view.key

sealed class Key

enum class Functional {

    CapsLock,
    ToSymbols,
    ToAdditionalSymbols,
    ToLetters,
    ToNumPad,
    Backspace,
    Enter,
    SwitchLanguage,
    Space;
}

open class FunctionalKey(val function: Functional, open val label: String) : Key() {

    class CapsLock(
        private var isCapsLock: Boolean = false,
        private var labels: Pair<String, String> = Pair("\u21EA", "\u21E7")
    ) : FunctionalKey(Functional.CapsLock, labels.second) {
        fun updateShift(isCapsLock: Boolean) {
            this.isCapsLock = isCapsLock
        }

        override val label: String
            get() = with(labels) { if (isCapsLock) first else second }
    }
}

sealed class PrintedKey(val alternatives: List<String>) : Key() {

    abstract val symbol: String

    class Symbol(
        override val symbol: String,
        alternatives: List<String> = emptyList()
    ) :
        PrintedKey(alternatives) {

    }

    class Letter(private val letter: String, alternatives: List<String> = emptyList()) :
        PrintedKey(alternatives) {

        private var isCapital: Boolean = false
        override val symbol: String
            get() = if (isCapital) letter.uppercase() else letter

        fun setCapital(isCapital: Boolean) {
            this.isCapital = isCapital
        }
    }
}