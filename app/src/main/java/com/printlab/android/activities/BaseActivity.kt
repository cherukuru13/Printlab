package com.printlab.android.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.printlab.android.task.ActiveInternetTask
import com.printlab.android.utils.DeviceMatrix
import com.printlab.android.utils.Logg


/**
 * [BaseActivity] is an abstract class extends [AppCompatActivity]
 */
abstract class BaseActivity : AppCompatActivity(), ActiveInternetTask.Callback {
    /**
     * Flag used to register [ConnectivityManager].CONNECTIVITY_ACTION event
     * By default it is -> true
     */
    private var canRegisterInternetEvents = true
    /**
     * [android.os.AsyncTask] used to check whether the active internet is there or not
     */
    private var activeInternetTask: ActiveInternetTask? = null

    /**
     * Receiver to receive CONNECTIVITY_ACTION events
     */
    private val mInternetReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            if (intent == null || intent.action == null) return
            when (intent.action) {
                //Internet state change callback
                ConnectivityManager.CONNECTIVITY_ACTION -> {
                    val connMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    val actNetworkInfo = connMgr?.activeNetworkInfo
                    if (actNetworkInfo != null) {
                        if (actNetworkInfo.isConnected) {
                            //if ConstantData.lastInternetState is unknown or inactive proceed with startBgInternetTask() else calls do nothing
                            if (lastInternetState != INTERNET_STATE_ACTIVE) {
                                startBgInternetTask()
                            }
                            return
                        }
                    }
                    lastInternetState = INTERNET_STATE_INACTIVE
                    OnNetworkChanged(false)
                }
                else -> {
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DeviceMatrix.initDeviceMetrics(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
    }

    override fun onResume() {
        super.onResume()
        //registers internet receiver
        registerInternetReceiver()
    }

    override fun onPause() {
        super.onPause()
        unregisterInternetReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (activeInternetTask != null && !activeInternetTask!!.isCancelled) {
            activeInternetTask!!.cancel(true)
        }
    }

    /**
     * Registers ConnectivityManager.CONNECTIVITY_ACTION events
     */
    private fun registerInternetReceiver() {
        //IntentFilter for registering ConnectivityManager.CONNECTIVITY_ACTION events
        val mInternetFilter = IntentFilter()
        mInternetFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        if (canRegisterInternetEvents) {
            registerReceiver(mInternetReceiver, mInternetFilter)
        }
    }

    /**
     * Unregisters ConnectivityManager.CONNECTIVITY_ACTION events
     */
    private fun unregisterInternetReceiver() {
        if (canRegisterInternetEvents) {

            // when tried to unregister the receiver which was not registered.
            // So to handle this try-catch is used
            try {
                unregisterReceiver(mInternetReceiver)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }

        }
    }

    /**
     * Executes [ActiveInternetTask] and checks for internet connection
     */
    fun startBgInternetTask() {
        activeInternetTask = ActiveInternetTask(this)
        activeInternetTask!!.execute()
    }

    /**
     * Calling this method in onCreate or onResume of child activity will stop registering for ConnectivityManager.CONNECTIVITY_ACTION events
     * It sets canRegisterInternetEvents to false
     */
    fun unregisterInternetEvents() {
        this.canRegisterInternetEvents = false
    }

    /**
     * Called whenever there is changes in internet connection
     * Either called from onReceive of [BroadcastReceiver] of this base activity or from [ActiveInternetTask]
     *
     * @param b state of the active internet
     */
    override fun OnNetworkChanged(b: Boolean) {
        Logg.v(TAG, "OnNetworkChanged base:: $b")
    }

    companion object {

        val INTERNET_STATE_UNKNOWN = -1
        val INTERNET_STATE_INACTIVE = 0
        val INTERNET_STATE_ACTIVE = 1
        private val TAG = "BaseActivity"
        /**
         * Last known internet state of the app
         * It can be INTERNET_STATE_UNKNOWN = -1, INTERNET_STATE_ACTIVE = 1 or  INTERNET_STATE_INACTIVE = 0
         */
        var lastInternetState = INTERNET_STATE_UNKNOWN
    }


}