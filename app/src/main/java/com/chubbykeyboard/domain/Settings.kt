package com.chubbykeyboard.domain

sealed class Settings(val title: String) {
    class RangedSettings(
        title: String,
        val description : String,
        val range: LongRange,
        val value: Long
    ) : Settings(title)
}
