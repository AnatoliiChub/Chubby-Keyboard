package com.chubbykeyboard.data

/**
 * Supported languages
 * @param code language code according to ISO 639.
 */
enum class SupportedLanguages(val code: String) {
    ENGLISH("en"),
    UKRAINIAN("uk");

    companion object {
        fun fromCode(code: String) = entries.firstOrNull { it.code == code }
    }
}