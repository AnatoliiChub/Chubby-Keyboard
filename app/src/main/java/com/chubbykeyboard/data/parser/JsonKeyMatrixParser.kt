package com.chubbykeyboard.data.parser

import android.content.Context
import com.chubbykeyboard.domain.keyboard.keys.FunctionalKey
import com.chubbykeyboard.domain.keyboard.keys.Key
import com.chubbykeyboard.domain.keyboard.keys.PrintedKey
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.InputStreamReader
import javax.inject.Inject

class JsonKeyMatrixParser @Inject constructor(
    @ApplicationContext val context: Context,
    private val gson: Gson
) : KeyMatrixParser {

    override fun parse(fileName: String): KeyMatrix{
        val assetManager = context.assets
        val inputStream = assetManager.open(fileName)
        val reader = InputStreamReader(inputStream)
        val jsonElement = JsonParser.parseReader(reader)
        val isNumPad = jsonElement.asJsonObject.has("additionalOptions")
        val clazz = if(isNumPad) KeyMatrix.NumPadMatrix::class.java else KeyMatrix::class.java
        return gson.fromJson(jsonElement, clazz)
    }
}

open class KeyMatrix(
    val matrix: List<List<Key>>
) {
    class NumPadMatrix(matrix: List<List<Key>>, val additionalOptions: List<PrintedKey>, val additionalButton : FunctionalKey) : KeyMatrix(matrix)
}