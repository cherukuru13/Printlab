package com.printlab.android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.printlab.android.R
import com.printlab.android.activities.MainActivity
import com.printlab.android.activities.OrderConfirmationActivity
import com.printlab.android.manager.NavigationManager
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
        view.changesPayment.setOnClickListener(this)
        view.changesDateTimeDetails.setOnClickListener(this)
        view.changeTo.setOnClickListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickEvents(view)
    }

    override fun onClick(v: View?) {

        when (v!!.id) {

            R.id.btn_placeOrder -> {

//                (activity as MainActivity).setContainerFrag(
//                    OrderConfirmationActivity.getInstance(),
//                    OrderConfirmationActivity.mTag
                activity!!.finish()
                NavigationManager.start<OrderConfirmationActivity>(activity!!)

            }

            R.id.changesPayment -> {

                (activity as MainActivity).setContainerFrag(PaymentDetailsFrag.getInstance(), PaymentDetailsFrag.mTag)

            }

            R.id.changesDateTimeDetails -> {

                (activity as MainActivity).setContainerFrag(
                    DeliveryDateTimeFrag.getInstance(),
                    DeliveryDateTimeFrag.mTag
                )


            }
            R.id.changeTo -> {


            }

        }

    }

}