package com.chubbykeyboard.data.parser

import com.chubbykeyboard.Functional
import com.chubbykeyboard.FunctionalKey
import com.chubbykeyboard.Key
import com.chubbykeyboard.PrintedKey
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import javax.inject.Inject

class KeyTypeAdapter @Inject constructor() : TypeAdapter<Key>() {

    override fun write(out: JsonWriter, value: Key) {
        //Implement write
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
                        FunctionalKey(
                            Functional.valueOf(
                                function
                            ), label
                        )
                    }
                }
            }

            else -> throw IllegalArgumentException("Unknown key type")
        }
    }
}