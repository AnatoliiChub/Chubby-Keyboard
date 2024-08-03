package com.chubbykeyboard.di

import com.chubbykeyboard.data.AvailableLocalesRepository
import com.chubbykeyboard.data.CachedKeyMatrixRepository
import com.chubbykeyboard.data.KeyMatrixRepository
import com.chubbykeyboard.data.KeyTypeAdapter
import com.chubbykeyboard.data.LocalesRepository
import com.chubbykeyboard.view.key.Key
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class DataModule {

    @Provides
    fun provideAlphabetKeyMatrixRepository(repo: CachedKeyMatrixRepository): KeyMatrixRepository {
        return repo
    }

    @Provides
    fun provideAvailableLocalesRepository(repo: AvailableLocalesRepository): LocalesRepository {
        return repo
    }

    @Provides
    fun provideGson(): Gson =
        GsonBuilder().registerTypeAdapter(Key::class.java, KeyTypeAdapter()).create()
}