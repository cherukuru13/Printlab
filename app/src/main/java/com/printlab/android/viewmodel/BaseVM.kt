package com.printlab.android.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper

abstract class BaseVM(application: Application) : AndroidViewModel(application) {

    companion object {
        const val TAG = "BaseVM"
    }

    /**
     * Handler to execute tasks in background
     */
    var mBgHandler: Handler? = null
    /**
     * Handler to execute tasks in UI(main-thread)
     */
    var mUiHandler: Handler = Handler(Looper.getMainLooper())
    /**
     * Thread, whose looper attached to [mBgHandler], so that we can execute tasks and runnable's in background
     */
    private var ht: HandlerThread? = null

    /**
     * Method to get context of the application.
     */
    fun application(): Application {
        return getApplication()
    }

    init {
        // Starting bg thread
        ht = HandlerThread(TAG)
        ht?.start()
        ////////////////////////

        // Assigning the mBgHandler to execute runnable's in 'ht' thread
        mBgHandler = Handler(ht?.looper)
    }

    override fun onCleared() {
        super.onCleared()
        // remove all unfinished callback
        mBgHandler?.removeCallbacksAndMessages(null)
        mUiHandler.removeCallbacksAndMessages(null)
        // quitting background thread
        ht?.quit()
    }
}