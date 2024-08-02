package com.chubbykeyboard.data

/**
 * Supported languages
 * @param code language code according to ISO 639.
 */
enum class SupportedLanguages(val code: String, val lettersFile: String, val symbolsFile: String) {
    ENGLISH("en", "en/letters.json", "en/symbols.json"),
    UKRAINIAN("uk", "uk/letters.json",  "uk/symbols.json"),;

    companion object {
        fun fromCode(code: String) = entries.firstOrNull { it.code == code }
    }
}