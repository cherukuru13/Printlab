package com.printlab.android.activities

import android.os.Bundle
import com.printlab.android.R
import com.printlab.android.manager.NavigationManager
import kotlinx.android.synthetic.main.activity_login_options.*


class LoginOptionsActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_options)

        register.setOnClickListener {

            NavigationManager.start<AddressDeliveryForMapActivity>(this)

        }
    }
}