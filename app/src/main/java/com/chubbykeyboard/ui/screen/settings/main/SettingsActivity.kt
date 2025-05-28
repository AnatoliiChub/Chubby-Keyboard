package com.chubbykeyboard.ui.screen.settings.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chubbykeyboard.R
import com.chubbykeyboard.ui.screen.settings.SettingsScreens
import com.chubbykeyboard.ui.screen.settings.SettingsScreens.SETTINGS
import com.chubbykeyboard.ui.screen.settings.about.AboutScreen
import com.chubbykeyboard.ui.screen.settings.accessebility.AccessibilitySettingsScreen
import com.chubbykeyboard.ui.screen.settings.debug.DebugScreen
import com.chubbykeyboard.ui.theme.ChubbyKeyboardTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ChubbyKeyboardTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { AppBar { if (!navController.popBackStack()) finish() } }) { innerPadding ->
                    NavHost(startDestination = SETTINGS, navController = navController) {
                        composable(SETTINGS) {
                            SettingsScreen(innerPadding = innerPadding) { navController.navigate(it) }
                        }
                        composable(SettingsScreens.ACCESSIBILITY) {
                            AccessibilitySettingsScreen(innerPadding = innerPadding)
                        }
                        composable(SettingsScreens.ABOUT) {
                            AboutScreen(innerPadding = innerPadding)
                        }
                        composable(SettingsScreens.DEBUG) {
                            DebugScreen(innerPadding = innerPadding)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(onBackClick: () -> Unit) {
    TopAppBar(title = { Text("Settings") }, navigationIcon = {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back), contentDescription = "Back"
            )
        }
    })
}
