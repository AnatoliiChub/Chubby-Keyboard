package com.chubbykeyboard.ui.screen.settings.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chubbykeyboard.ui.screen.settings.SettingsScreens

@Composable
fun SettingsScreen(
    innerPadding: PaddingValues,
    viewModel: SettingsViewModel = hiltViewModel(),
    onItemClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp)
    ) {
        viewModel.uiState.collectAsStateWithLifecycle().value.map {
            val label = when (it) {
                SettingsScreens.ACCESSIBILITY -> "Accessibility"
                SettingsScreens.ABOUT -> "About"
                SettingsScreens.DEBUG -> "Debug"
                else -> throw IllegalArgumentException("Unknown screen")
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(it) }) {
                Text(text = label, modifier = Modifier.padding(16.dp), fontSize = 20.sp)
            }
        }
    }
}
