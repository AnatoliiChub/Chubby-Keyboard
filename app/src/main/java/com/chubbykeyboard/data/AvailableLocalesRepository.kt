package com.chubbykeyboard.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject


class AvailableLocalesRepository @Inject constructor(@ApplicationContext private val context: Context) :
    LocalesRepository {

    override fun getLocales(): List<Locale> {
        val locales = context.resources.configuration.locales
        val size = locales.size()
        val result = mutableListOf<Locale>()
        for (i in 0..<size) {
            result.add(locales.get(i))
        }
        return result
    }
}