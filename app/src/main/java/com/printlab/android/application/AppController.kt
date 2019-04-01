package com.printlab.android.application

import android.support.multidex.MultiDexApplication
import android.view.View
import com.printlab.android.utils.Logg

class AppController : MultiDexApplication() {

    companion object {

        public var mInstance: AppController? = null

        fun getInstance(): AppController {
            Logg.v("AppController ", "pref data AppController companion called")
            return mInstance!!
        }
    }


    fun hideSystemUI(decorView: View?) {
        if (decorView != null) {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }


    override fun onCreate() {
        super.onCreate()
        mInstance = this
        Logg.v("AppController ", " pref data AppController onCreate called")

    }

    override fun onTerminate() {
        super.onTerminate()
        Logg.v("AppController ", " pref data AppController onTerminate called")

    }

}