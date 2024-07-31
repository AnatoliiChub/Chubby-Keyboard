package com.chubbykeyboard.view.popup.positioinprovider

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider
import com.chubbykeyboard.view.model.ScreenSize

/**
 * Calculates the position of the popup with taking into account the screen boundaries on the device.
 */
class BorderHandledPositionProvider(
    private val alignment: Alignment,
    private val offset: IntOffset,
    private val screenSize: ScreenSize,
    private val positionProvider: PopupPositionProvider = AlignmentOffsetPositionProvider(alignment, offset)
) : PopupPositionProvider {

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {

        val alignmentOffset =
            positionProvider.calculatePosition(anchorBounds, windowSize, layoutDirection, popupContentSize)

        val screenWidth = screenSize.width
        val screenHeight = screenSize.height
        val x = alignmentOffset.x
        val y = alignmentOffset.y
        val maxX = screenWidth - popupContentSize.width
        val maxY = screenHeight - popupContentSize.height

        if (x < 0 || x > maxX || y < 0 || y > maxY) {
            val newX = if (x > maxX) maxX else if (x < 0) 0 else x
            val newY = if (y > maxY) maxY else if (y < 0) 0 else y
            return IntOffset(newX, newY)
        }

        return alignmentOffset
    }

}