package com.chubbykeyboard.view.keyboard

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView

class ChubbyKeyboardView(context: Context) : AbstractComposeView(context) {

    @Composable
    override fun Content() {
        ChubbyKeyboard()
    }
}