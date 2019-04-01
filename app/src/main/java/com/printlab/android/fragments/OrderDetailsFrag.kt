package com.printlab.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.printlab.android.R

class OrderDetailsFrag : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.frag_order_details, container, false)

    }

    companion object {

        var mInstance: OrderDetailsFrag? = null
        val mTag = "OrderDetailsFrag"

        fun getInstance(): OrderDetailsFrag {

            if (mInstance == null) {

                return OrderDetailsFrag()
            }
            return mInstance!!
        }

    }
}