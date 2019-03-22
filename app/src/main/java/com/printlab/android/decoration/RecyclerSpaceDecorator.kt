package com.cmnbk.madhuramshop.adapter.decoration

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Arun Shankar on 20/9/18.
 * [RecyclerSpaceDecorator] extends [RecyclerView.ItemDecoration] used to
 * decorate [RecyclerView] by giving necessary spacing between items
 */
class RecyclerSpaceDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = 1

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = 1
        }
    }

}