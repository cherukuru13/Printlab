package com.printlab.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.printlab.android.R

class ProductDetailsFrag : BaseFragment() {

    companion object {

        var mInstance: ProductDetailsFrag? = null
        val mTag = "ProductDetailsFrag"

        fun getInstance(): ProductDetailsFrag {

            if (mInstance == null) {

                return ProductDetailsFrag()
            }
            return mInstance!!
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_product_details_screen, container, false)
    }
}