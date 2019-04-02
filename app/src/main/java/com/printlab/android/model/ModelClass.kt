package com.printlab.android.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

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


@Parcelize
data class GroceryModel(
    var name: String,
    var sale_price: Double,
    var brand: String,
    var id: String,
    var description: String,
    var downloadUrl: String? = null,
    var image: LinkedHashMap<String, String>? = null,
    var categories: LinkedHashMap<String, Boolean>? = null,
    var productVariant: @RawValue Any? = null,
    var variant: String? = null, var mProductCount: Int = 1
) : Parcelable {


}


//@SerializedName("Test")