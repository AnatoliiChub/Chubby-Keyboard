package com.example.chubbykeyboard.view.popup

import android.util.Log
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider
import com.example.chubbykeyboard.view.model.ScreenSize

// TODO: Refactor to three different classes : AlignmentOffsetProvider, HandleEdgeBordersProvider and TrackablePositionProvider
class TrackablePositionProvider(
    val alignment: Alignment,
    val offset: IntOffset,
    val onPopupPositionChanged: (IntOffset) -> Unit,
    val screenSize: ScreenSize
) : PopupPositionProvider {


    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {

        val anchorAlignmentPoint = alignment.align(
            IntSize.Zero,
            anchorBounds.size,
            layoutDirection
        )
        // Note the negative sign. Popup alignment point contributes negative offset.
        val popupAlignmentPoint = -alignment.align(
            IntSize.Zero,
            popupContentSize,
            layoutDirection
        )
        val resolvedUserOffset = IntOffset(
            offset.x * (if (layoutDirection == LayoutDirection.Ltr) 1 else -1),
            offset.y
        )

        var result = anchorBounds.topLeft +
                anchorAlignmentPoint +
                popupAlignmentPoint +
                resolvedUserOffset
        Log.d("Popup", "offset: $result")

        val screenWidth = screenSize.width
        val screenHeight = screenSize.height
        // TODO: fix hardcoded values for TopCenter alignment
        val x = offset.x + anchorBounds.left - popupContentSize.width / 2
        val y = offset.y + anchorBounds.top
        val maxX = screenWidth - popupContentSize.width
        val maxY = screenHeight - popupContentSize.height
        Log.d("Popup", "max position: $maxX, $maxY")
        Log.d("Popup", "x, y: $x, $y")
        if (x > maxX || x < 0 || y > maxY || y < 0) {
            val newX = if (x > maxX) maxX else if (x < 0) 0 else x
            val newY = if (y > maxY) maxY else if (y < 0) 0 else y
            Log.d("Popup", "new position: $newX, $newY")
            result = IntOffset(newX, newY)
        }

        onPopupPositionChanged(result)
        return result
    }
}