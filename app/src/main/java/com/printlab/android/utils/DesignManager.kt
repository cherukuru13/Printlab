package com.printlab.android.utils

import android.view.View
import android.view.ViewGroup

/**
 *
 */
object DesignManager {

    @JvmOverloads
    fun initParams(view: View, mViewWidth: Int = 0, mViewHeight: Int = 0,
                   mLeftMargin: Int = 0, mTopMargin: Int = 0,
                   mRightMargin: Int = 0, mBottomMargin: Int = 0) {
        val params = view.layoutParams as ViewGroup.MarginLayoutParams
        if (mViewWidth != 0) params.width = mViewWidth
        if (mViewHeight != 0) params.height = mViewHeight
        if (mLeftMargin != 0) params.leftMargin = mLeftMargin
        if (mTopMargin != 0) params.topMargin = mTopMargin
        if (mRightMargin != 0) params.rightMargin = mRightMargin
        if (mBottomMargin != 0) params.bottomMargin = mBottomMargin
        view.layoutParams = params
    }


}