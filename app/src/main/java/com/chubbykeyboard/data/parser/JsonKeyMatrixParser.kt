package com.chubbykeyboard.data.parser

import android.content.Context
import com.chubbykeyboard.Key
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStreamReader
import javax.inject.Inject

class JsonKeyMatrixParser @Inject constructor(
    @ApplicationContext val context: Context,
    private val gson: Gson
) : KeyMatrixParser {

    override fun parse(fileName: String): List<List<Key>> {
        val assetManager = context.assets
        val inputStream = assetManager.open(fileName)
        val reader = InputStreamReader(inputStream)
        val jsonElement = JsonParser.parseReader(reader)
        val jsonArray = jsonElement.asJsonArray

        return jsonArray.map { rowElement ->
            rowElement.asJsonArray.map { keyElement ->
                gson.fromJson(keyElement, Key::class.java)
            }
        }
    }
}