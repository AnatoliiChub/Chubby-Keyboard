package com.chubbykeyboard.ui.screen.settings.accessebility

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chubbykeyboard.domain.Settings
import com.chubbykeyboard.ui.view.settings.RangedSetting

@Composable
fun AccessibilitySettingsScreen(
    innerPadding: PaddingValues, viewModel: AccessibilitySettingsViewModel = hiltViewModel()
) {
    val settings = viewModel.uiState.collectAsStateWithLifecycle().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        items(settings.size) { index ->
            when (val item = settings[index]) {
                is Settings.RangedSettings -> {
                    RangedSetting(item) {
                        viewModel.onDebounceChanged(it.toLong())
                    }
                }
            }
        }
    }
}
