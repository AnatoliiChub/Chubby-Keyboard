package com.chubbykeyboard.data.repo

import androidx.annotation.StringRes

interface StringsRepository {

    suspend fun getString(@StringRes id: Int): String
}