package com.chubbykeyboard.ui.screen

sealed class Settings(val title: String) {
    class RangedSettings(
        title: String,
        val description : String,
        val min: Long,
        val max: Long,
        val value: Long
    ) : Settings(title)
}