package com.printlab.android.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NavHeaderModel(
     var mHeaderTitle: String,
    var mRes: Int = -1,
    var isHeaderSel: Boolean = false,
    var hasChild: Boolean = false,
    var isNew: Boolean = false, var mChildList: ArrayList<NavChildModel> = ArrayList()
) : Parcelable

@Parcelize
data class NavChildModel(var mChildTitle: String, var isChildSelected: Boolean = false) : Parcelable {


}


//@SerializedName("Test")