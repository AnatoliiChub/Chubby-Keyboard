package com.chubbykeyboard.ui.screen.settings.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.chubbykeyboard.BuildConfig

@Composable
fun AboutScreen(innerPadding: PaddingValues,) {
    Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
        Text("${BuildConfig.APPLICATION_ID} ${BuildConfig.VERSION_NAME}(${BuildConfig.VERSION_CODE})")
    }
}
