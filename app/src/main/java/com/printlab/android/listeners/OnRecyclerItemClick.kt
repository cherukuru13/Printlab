package com.printlab.android.listeners

interface OnRecyclerItemClick<T> {


    fun onItemClick(pos: Int, viewId: Int, t: T)


}