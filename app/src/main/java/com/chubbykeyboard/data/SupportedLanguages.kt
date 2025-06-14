package com.chubbykeyboard.data

/**
 * Supported languages
 * @param code language code according to ISO 639.
 */
enum class SupportedLanguages(
    val code: String,
    val lettersFile: String,
    val symbolsFile: String,
    val additionalSymbolsFile: String,
    val numpadFile: String
) {
    ENGLISH(
        "en",
        "en/letters.json",
        "en/symbols.json",
        "en/additional_symbols.json",
        "en/numpad.json"
    ),
    UKRAINIAN(
        "uk",
        "uk/letters.json",
        "uk/symbols.json",
        "uk/additional_symbols.json",
        "uk/numpad.json"
    ), ;

    companion object {
        fun fromCode(code: String) = entries.firstOrNull { it.code == code }
    }
}