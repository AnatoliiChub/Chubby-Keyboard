package com.chubbykeyboard.di

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.chubbykeyboard.data.repo.AccessibilitySettingsRepositoryImpl
import com.chubbykeyboard.domain.GetCurrentSupportedLocaleUseCase
import com.chubbykeyboard.domain.ProvideKeyMatrixUseCase
import com.chubbykeyboard.domain.SwitchLanguageUseCase
import com.chubbykeyboard.service.ChubbyHapticManager
import com.chubbykeyboard.service.HapticManager
import com.chubbykeyboard.ui.view.keyboard.ChubbyKeyboardViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent

@Module(includes = [DataModule::class])
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @Provides
    fun provideHapticManager(hapticManager: ChubbyHapticManager) : HapticManager = hapticManager

    @Provides
    fun provideChubbyKeyboardViewModelFactory(
        provideKeyMatrixUseCase: ProvideKeyMatrixUseCase,
        switchLanguageUseCase: SwitchLanguageUseCase,
        getCurrentSupportedLocaleUseCase: GetCurrentSupportedLocaleUseCase,
        accessibilitySettingsRepositoryImpl: AccessibilitySettingsRepositoryImpl,
        hapticManager: HapticManager
    ) = viewModelFactory {
        initializer {
            ChubbyKeyboardViewModel(
                provideKeyMatrixUseCase,
                switchLanguageUseCase,
                getCurrentSupportedLocaleUseCase,
                accessibilitySettingsRepositoryImpl,
                hapticManager
            )
        }
    }
}