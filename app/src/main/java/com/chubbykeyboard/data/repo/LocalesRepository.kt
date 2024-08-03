package com.chubbykeyboard.data.repo

import java.util.Locale

interface LocalesRepository {

    fun getLocales(): List<Locale>
}