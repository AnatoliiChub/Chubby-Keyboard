package com.example.chubbykeyboard.view

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import com.example.chubbykeyboard.view.model.ScreenSize

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.debounceCombinedClickable(
    interactionSource: MutableInteractionSource,
    indication: Indication? = null,
    debounceInterval: Long = 350,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
): Modifier {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    return this.composed {
        Modifier.combinedClickable(interactionSource = interactionSource, indication = indication, onClick = {
            val currentTime = System.currentTimeMillis()
            if ((currentTime - lastClickTime) < debounceInterval) return@combinedClickable
            lastClickTime = currentTime
            onClick()
        }, onLongClick = onLongClick)
    }
}

@Composable
fun Configuration.screenSize(): ScreenSize{
    val configuration = LocalConfiguration.current
    val density = LocalContext.current.resources.displayMetrics.density
    val screenWidth = configuration.screenWidthDp * density
    val screenHeight = configuration.screenHeightDp * density
    return ScreenSize(screenWidth.toInt(), screenHeight.toInt())
}
