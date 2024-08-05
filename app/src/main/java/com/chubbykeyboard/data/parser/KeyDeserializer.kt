package com.chubbykeyboard.data.parser

import com.chubbykeyboard.keyboard.keys.FunctionalKey
import com.chubbykeyboard.keyboard.keys.Key
import com.chubbykeyboard.keyboard.keys.PrintedKey
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class KeyDeserializer : JsonDeserializer<Key> {

    private val gson = Gson()

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Key {
        val jsonObject = json.asJsonObject
        val jsonString = json.toString()
        return when {
            jsonObject.has("letter") -> gson.fromJson(jsonString, PrintedKey.Letter::class.java)
            jsonObject.has("symbol") -> gson.fromJson(jsonString, PrintedKey.Symbol::class.java)
            jsonObject.has("function") -> {
                if (jsonObject.get("function").asString == "CapsLock") {
                    gson.fromJson(jsonString, FunctionalKey.CapsLock::class.java)
                } else {
                    gson.fromJson(jsonString, FunctionalKey::class.java)
                }
            }

            else -> throw JsonParseException("Unknown key type  $jsonString")
        }
    }
}
