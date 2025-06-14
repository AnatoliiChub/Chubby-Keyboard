package com.chubbykeyboard.domain.keyboard.keys

sealed class Key

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

    override fun toString(): String {
        return "FunctionalKey(function=$function, label='$label')"
    }
}

sealed class PrintedKey(val alternatives: String?) : Key() {

    abstract val symbol: String

    class Symbol(
        override val symbol: String,
        alternatives: String?
    ) : PrintedKey(alternatives)

    class Letter(private val letter: String, alternatives: String?) :
        PrintedKey(alternatives) {

        private var isCapital: Boolean = false
        override val symbol: String
            get() = if (isCapital) letter.uppercase() else letter

        fun setCapital(isCapital: Boolean) {
            this.isCapital = isCapital
        }
    }
}
