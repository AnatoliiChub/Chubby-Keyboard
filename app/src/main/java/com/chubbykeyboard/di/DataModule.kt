package com.chubbykeyboard.di

import com.chubbykeyboard.data.KeyMatrixRepository
import com.chubbykeyboard.data.AvailableLocalesRepository
import com.chubbykeyboard.data.CachedKeyMatrixRepository
import com.chubbykeyboard.data.LocalesRepository
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
}