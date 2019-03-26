package com.printlab.android.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.printlab.android.R
import kotlinx.android.synthetic.main.activity_zipcode_success.*


class ZipCodeSuccess : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zipcode_success)

        btn_email.setOnClickListener { _ -> startActivity(Intent(this@ZipCodeSuccess, SignUpActivity::class.java)) }
        btn_phno.setOnClickListener { _ -> startActivity(Intent(this@ZipCodeSuccess, PhoneAuthActivity::class.java)) }
        fb_login.setOnClickListener { _ ->

        }

    }
}
