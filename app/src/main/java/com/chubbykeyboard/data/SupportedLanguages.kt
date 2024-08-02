package com.chubbykeyboard.data

/**
 * Supported languages
 * @param code language code according to ISO 639.
 */
enum class SupportedLanguages(val code: String, val keyFile: String) {
    ENGLISH("en", "en/alphabet.json"),
    UKRAINIAN("uk", "uk/alphabet.json");

    companion object {
        fun fromCode(code: String) = entries.firstOrNull { it.code == code }
    }
}