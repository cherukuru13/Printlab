package com.printlab.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.printlab.android.R

class DeliveryDateTimeFrag : BaseFragment() {


    companion object {

        var mInstance: DeliveryDateTimeFrag? = null
        val mTag = "DeliveryDateTimeFrag"

        fun getInstance(): DeliveryDateTimeFrag {

            if (mInstance == null) {

                return DeliveryDateTimeFrag()
            }
            return mInstance!!
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_delivery_date_time, container, false)
    }

}