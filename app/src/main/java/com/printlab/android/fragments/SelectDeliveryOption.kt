package com.printlab.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.printlab.android.R

class SelectDeliveryOption : BaseFragment() {

    companion object {

        var mInstance: SelectDeliveryOption? = null
        val mTag = "SelectDeliveryOption"

        fun getInstance(): SelectDeliveryOption {

            if (mInstance == null) {

                return SelectDeliveryOption()
            }
            return mInstance!!
        }

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_select_delivery_option, container, false)
    }

}