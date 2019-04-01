package com.printlab.android.activities

import android.os.Bundle
import android.provider.Settings
import com.cmnbk.madhuramshop.manager.DialogManager
import com.facebook.FacebookSdk
import com.printlab.android.R
import com.printlab.android.manager.NavigationManager
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : BaseActivity(), DialogManager.Callback {
    override fun onDialogSuccess(tag: String) {

        NavigationManager.setAction(this, Settings.ACTION_WIFI_SETTINGS)
    }

    override fun onDialogCancel(tag: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        FacebookSdk.sdkInitialize(this.applicationContext)

        getstarted.setOnClickListener {

//            NavigationManager.start<MainActivity>(this)


            if (BaseActivity.lastInternetState == 1) {
                NavigationManager.start<MainActivity>(this)

            } else {
                NavigationManager.start<LoginOptionsActivity>(this)

//                DialogManager.dialog(this, "SETTINGS", "Enable Mobile or Wifi Data! Go to settings menu?", this)
            }
        }
    }

}