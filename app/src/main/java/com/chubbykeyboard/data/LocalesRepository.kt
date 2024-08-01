package com.chubbykeyboard.data

import java.util.Locale

interface LocalesRepository {

    fun getLocales(): List<Locale>
}