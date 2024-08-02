package com.chubbykeyboard.data

import android.content.Context
import com.chubbykeyboard.view.key.Functional
import com.chubbykeyboard.view.key.FunctionalKey
import com.chubbykeyboard.view.key.Key
import com.chubbykeyboard.view.key.PrintedKey
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStreamReader
import javax.inject.Inject

class JsonKeyMatrixParser @Inject constructor(
    @ApplicationContext val context: Context
) : KeyMatrixParser {

    override fun parse(fileName: String): List<List<Key>> {
        val assetManager = context.assets
        val inputStream = assetManager.open(fileName)
        val reader = InputStreamReader(inputStream)
        val jsonElement = JsonParser.parseReader(reader)
        val jsonArray = jsonElement.asJsonArray

        return jsonArray.map { rowElement ->
            rowElement.asJsonArray.map { keyElement ->
                parseKey(keyElement.asJsonObject)
            }
        }
    }

    private fun parseKey(jsonObject: JsonObject): Key {
        return when {
            jsonObject.has("letter") -> {
                val letter = jsonObject.get("letter").asString
                val isCapital = jsonObject.get("isCapital")?.asBoolean ?: false
                val alternatives = jsonObject.get("alternatives")?.asJsonArray?.map { it.asString } ?: emptyList()
                PrintedKey.Letter(letter, alternatives).apply { setCapital(isCapital) }
            }

            jsonObject.has("symbol") -> {
                val symbol = jsonObject.get("symbol").asString
                val alternatives = jsonObject.get("alternatives")?.asJsonArray?.map { it.asString } ?: emptyList()
                PrintedKey.Symbol(symbol, alternatives)
            }

            jsonObject.has("function") -> {
                when (val function = jsonObject.get("function").asString) {
                    "CapsLock" -> FunctionalKey.CapsLock(jsonObject.get("isCapsLock")?.asBoolean ?: false)
                    "ToSymbols" -> FunctionalKey(Functional.ToSymbols)
                    "Backspace" -> FunctionalKey(Functional.Backspace)
                    "Enter" -> FunctionalKey(Functional.Enter)
                    "SwitchLanguage" -> FunctionalKey(Functional.SwitchLanguage)
                    "Space" -> FunctionalKey(Functional.Space)
                    else -> throw IllegalArgumentException("Unknown function: $function")
                }
            }

            else -> throw IllegalArgumentException("Unknown key type")
        }
    }
}