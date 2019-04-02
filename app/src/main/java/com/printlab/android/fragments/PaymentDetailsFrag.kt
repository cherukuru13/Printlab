package com.printlab.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.printlab.android.R

class PaymentDetailsFrag : BaseFragment() {

    companion object {

        var mInstance: PaymentDetailsFrag? = null
        val mTag = "PaymentDetailsFrag"

        fun getInstance(): PaymentDetailsFrag {

            if (mInstance == null) {

                return PaymentDetailsFrag()
            }
            return mInstance!!
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_payment_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}