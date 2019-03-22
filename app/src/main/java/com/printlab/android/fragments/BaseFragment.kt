package com.printlab.android.fragments

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager

/**
 * [BaseFragment] is a abstract `android.app.Fragment` used to initialize
 * and implement common functionality across all other fragments
 */
abstract class BaseFragment : Fragment() {
    private var mHT: HandlerThread? = null
    var mUiHandler: Handler? = null
    var mBgHandler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHT = HandlerThread("BaseFragment")
        mHT?.start()
        mUiHandler = Handler()
        mBgHandler = Handler(mHT?.looper)
    }

    fun registerReceiver(receiver: BroadcastReceiver, action: String) {
        if (activity == null) return
        val filter = IntentFilter(action)
        LocalBroadcastManager.getInstance(activity!!)
                .registerReceiver(receiver, filter)
    }

    fun unregisterReceiver(receiver: BroadcastReceiver) {
        if (activity == null) return
        LocalBroadcastManager.getInstance(activity!!)
                .unregisterReceiver(receiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        mUiHandler?.removeCallbacksAndMessages(null)
        mBgHandler?.removeCallbacksAndMessages(null)
        mHT?.quit()
    }

}