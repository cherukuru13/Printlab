package com.printlab.android.activities

import android.os.Bundle
import com.printlab.android.R
import com.printlab.android.activities.BaseActivity
import com.printlab.android.manager.NavigationManager
import kotlinx.android.synthetic.main.activity_order_confirmation.*


class OrderConfirmationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation)
        init()
    }

    private fun init() {

        btn_orderConfirm.setOnClickListener {

            finish()
            NavigationManager.start<MainActivity>(this)


        }

    }


}