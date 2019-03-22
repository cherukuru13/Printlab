package com.printlab.android.application

import android.support.multidex.MultiDexApplication
import com.printlab.android.utils.Logg

class AppController : MultiDexApplication() {

    companion object {

        private var mInstance: AppController? = null

        fun getInstance(): AppController {
            Logg.v("AppController ", "pref data AppController companion called")
            return mInstance!!
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