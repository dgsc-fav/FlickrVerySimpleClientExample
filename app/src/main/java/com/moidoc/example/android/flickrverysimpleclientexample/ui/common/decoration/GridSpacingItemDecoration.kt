package com.moidoc.example.android.flickrverysimpleclientexample.ui.common.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

// from https://stackoverflow.com/a/30701422/9090703
class GridSpacingItemDecoration(private val spanCount: Int, private val spacingPercentage: Float, private val includeEdge: Boolean) :
    RecyclerView.ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        val width = parent.width
        val horizMarginPx = (width * spacingPercentage).roundToInt()
        val verticalMarginPx = horizMarginPx // as horiz

        if (includeEdge) {
            outRect.left = horizMarginPx - column * horizMarginPx / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * horizMarginPx / spanCount // (column + 1) * ((1f / spanCount) * spacing)

            if (position >= spanCount) { // top edge
                outRect.top = verticalMarginPx
            }
            outRect.bottom = 0 // item bottom
        } else {
            outRect.left = column * horizMarginPx / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right = horizMarginPx - (column + 1) * horizMarginPx / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = verticalMarginPx // item top
            }
        }
    }
}