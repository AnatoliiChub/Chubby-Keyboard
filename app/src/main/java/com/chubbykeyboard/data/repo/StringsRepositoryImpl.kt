package com.chubbykeyboard.data.repo

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringsRepositoryImpl @Inject constructor(@ApplicationContext val context: Context) : StringsRepository {

    override suspend fun getString(id: Int): String {
        return context.getString(id)
    }
}