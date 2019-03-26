package com.printlab.android.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.printlab.android.R
import com.printlab.android.manager.NavigationManager
import kotlinx.android.synthetic.main.activity_zip_code.*


class ZipCode : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zip_code)

        btn_check.setOnClickListener {
            NavigationManager.start<ZipCodeSuccess>(this)

        }
    }
}
