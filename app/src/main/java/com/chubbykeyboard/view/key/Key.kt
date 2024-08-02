package com.chubbykeyboard.view.key

sealed class Key

enum class Functional(val labels: List<String>) {

    CapsLock(listOf("\u21E7", "\u21EA")),
    ToSymbols(listOf("?123")),
    Backspace(listOf("⌫")),
    Enter(listOf("\u23CE")),
    SwitchLanguage(listOf("\uD83C\uDF10\uFE0E")),
    Space(listOf("\u2423"));
}

open class FunctionalKey(val function: Functional) : Key() {

    open val label: String = function.labels.first()

    class CapsLock(
        private var isCapsLock: Boolean = false,
    ) : FunctionalKey(Functional.CapsLock) {
        fun updateShift(isCapsLock: Boolean) {
            this.isCapsLock = isCapsLock
        }

        override val label: String
            get() = with(function.labels) { if (isCapsLock) first() else last() }
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