package com.printlab.android.utils

import android.app.Activity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager

object RecyclerViewUtils {


     fun getLinearLayoutManager(activity: Activity,orientation:Int): LinearLayoutManager {

        return LinearLayoutManager(activity, orientation, false)

    }


     fun getGridLayoutManager(activity: Activity, columns: Int): GridLayoutManager {


        return GridLayoutManager(activity, columns)

    }


}