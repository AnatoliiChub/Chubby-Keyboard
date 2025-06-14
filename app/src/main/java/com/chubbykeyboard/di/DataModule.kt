package com.chubbykeyboard.di

import com.chubbykeyboard.data.parser.KeyDeserializer
import com.chubbykeyboard.data.repo.AccessibilitySettingsRepository
import com.chubbykeyboard.data.repo.AccessibilitySettingsRepositoryImpl
import com.chubbykeyboard.data.repo.AvailableLocalesRepository
import com.chubbykeyboard.data.repo.CachedKeyMatrixRepository
import com.chubbykeyboard.data.repo.KeyMatrixRepository
import com.chubbykeyboard.data.repo.LocalesRepository
import com.chubbykeyboard.data.repo.StringsRepository
import com.chubbykeyboard.data.repo.StringsRepositoryImpl
import com.chubbykeyboard.domain.keyboard.keys.Key
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
    fun provideAccessibilitySettingsRepository(repo: AccessibilitySettingsRepositoryImpl): AccessibilitySettingsRepository {
        return repo
    }

    @Provides
    fun provideStringRepository(repo: StringsRepositoryImpl): StringsRepository {
        return repo
    }

    @Provides
    fun provideGson(): Gson =
        GsonBuilder()
            .registerTypeHierarchyAdapter(Key::class.java, KeyDeserializer())
            .create()
}