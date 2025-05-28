package com.chubbykeyboard.ui.view.keyboard

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView

@SuppressLint("ViewConstructor")
class ChubbyKeyboardView(context: Context, private val viewModel: ChubbyKeyboardViewModel) :
    AbstractComposeView(context) {

    @Composable
    override fun Content() {
        ChubbyKeyboard(viewModel)
    }
}
