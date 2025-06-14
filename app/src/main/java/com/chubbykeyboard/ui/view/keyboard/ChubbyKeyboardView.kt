package com.chubbykeyboard.ui.view.keyboard

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AbstractComposeView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

@SuppressLint("ViewConstructor")
class ChubbyKeyboardView(context: Context, private val viewModel: ChubbyKeyboardViewModel) :
    AbstractComposeView(context) {

    private var bottomPadding: MutableState<Int> = mutableIntStateOf(0)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        launchInsetListener()
    }

    private fun launchInsetListener() {
        ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets ->
            val density = context.resources.displayMetrics.density
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            bottomPadding.value = insets.bottom / density.toInt()
            WindowInsetsCompat.CONSUMED
        }
    }

    @Composable
    override fun Content() {
        ChubbyKeyboard(bottomPadding, viewModel)
    }
}
