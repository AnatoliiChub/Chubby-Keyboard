package com.chubbykeyboard.ui.view.popup.positioinprovider

import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.toOffset
import androidx.compose.ui.window.PopupPositionProvider

class TrackablePositionProvider(
    val alignment: Alignment,
    val offset: IntOffset,
    val onPopupPositionChanged: (Offset) -> Unit,
    private val positionProvider: PopupPositionProvider = BorderHandledPositionProvider(alignment, offset)
) : PopupPositionProvider {

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val result = positionProvider.calculatePosition(anchorBounds, windowSize, layoutDirection, popupContentSize)
        onPopupPositionChanged(result.toOffset())
        return result
    }
}
