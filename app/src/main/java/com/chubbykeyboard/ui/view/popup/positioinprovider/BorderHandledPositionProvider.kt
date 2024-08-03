package com.chubbykeyboard.ui.view.popup.positioinprovider

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider

/**
 * Calculates the position of the popup with taking into account the screen boundaries on the device.
 */
class BorderHandledPositionProvider(
    private val alignment: Alignment,
    private val offset: IntOffset,
    private val positionProvider: PopupPositionProvider = AlignmentOffsetPositionProvider(alignment, offset)
) : PopupPositionProvider {

    companion object {
        private const val TOP_BORDER = -240
        private const val START_BORDER = 0
    }

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {

        val alignmentOffset =
            positionProvider.calculatePosition(anchorBounds, windowSize, layoutDirection, popupContentSize)

        val screenWidth = windowSize.width
        val screenHeight = windowSize.height
        val x = alignmentOffset.x
        val y = alignmentOffset.y
        val maxX = screenWidth - popupContentSize.width
        val maxY = screenHeight - popupContentSize.height

        if (x < START_BORDER || x > maxX || y < TOP_BORDER || y > maxY) {
            val newX = if (x > maxX) maxX else if (x < START_BORDER) START_BORDER else x
            val newY = if (y > maxY) maxY else if (y < TOP_BORDER) TOP_BORDER else y
            return IntOffset(newX, newY)
        }

        return alignmentOffset
    }

}