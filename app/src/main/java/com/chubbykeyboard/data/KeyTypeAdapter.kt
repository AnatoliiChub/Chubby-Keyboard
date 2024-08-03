package com.chubbykeyboard.data

import com.chubbykeyboard.view.key.Functional
import com.chubbykeyboard.view.key.FunctionalKey
import com.chubbykeyboard.view.key.Key
import com.chubbykeyboard.view.key.PrintedKey
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import javax.inject.Inject

class KeyTypeAdapter @Inject constructor() : TypeAdapter<Key>() {

    override fun write(out: JsonWriter, value: Key) {
        val jsonObject = JsonObject()
        when (value) {
            is PrintedKey.Letter -> jsonObject.addProperty("letter", value.symbol)
            is PrintedKey.Symbol -> jsonObject.addProperty("symbol", value.symbol)
            is FunctionalKey.CapsLock -> jsonObject.addProperty("function", "CapsLock")
            is FunctionalKey -> jsonObject.addProperty("function", value.function.name)
        }
    }

    override fun read(reader: JsonReader): Key {
        val jsonObject = JsonParser.parseReader(reader).asJsonObject
        return when {
            jsonObject.has("letter") -> {
                val letter = jsonObject.get("letter").asString
                val alternatives = jsonObject.get("alternatives")?.asJsonArray?.map { it.asString } ?: emptyList()
                val isCapital = jsonObject.get("isCapital")?.asBoolean ?: false
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
                    else -> {
                        val label = jsonObject.get("label")?.asString ?: ""
                        FunctionalKey(Functional.valueOf(function), label)
                    }
                }
            }

            else -> throw IllegalArgumentException("Unknown key type")
        }
    }
}