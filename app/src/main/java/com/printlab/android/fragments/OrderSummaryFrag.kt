package com.printlab.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.printlab.android.R
import kotlinx.android.synthetic.main.frag_order_summary.view.*


class OrderSummaryFrag : BaseFragment(), View.OnClickListener {


    companion object {

        var mInstance: OrderSummaryFrag? = null
        val mTag = "OrderSummaryFrag"

        fun getInstance(): OrderSummaryFrag {

            if (mInstance == null) {

                return OrderSummaryFrag()
            }
            return mInstance!!
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_order_summary, container, false)
    }

    private fun setClickEvents(view: View) {
        view.btn_placeOrder.setOnClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickEvents(view)
    }

    override fun onClick(v: View?) {

    }

}