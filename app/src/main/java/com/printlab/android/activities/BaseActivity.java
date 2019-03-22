package com.printlab.android.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.printlab.android.task.ActiveInternetTask;
import com.printlab.android.utils.DeviceMatrix;
import com.printlab.android.utils.Logg;


/**
 * {@link BaseActivity} is an abstract class extends {@link AppCompatActivity}
 */
public abstract class BaseActivity extends AppCompatActivity implements ActiveInternetTask.Callback {

    public static final int INTERNET_STATE_UNKNOWN = -1;
    public static final int INTERNET_STATE_INACTIVE = 0;
    public static final int INTERNET_STATE_ACTIVE = 1;
    private static final String TAG = "BaseActivity";
    /**
     * Last known internet state of the app
     * It can be INTERNET_STATE_UNKNOWN = -1, INTERNET_STATE_ACTIVE = 1 or  INTERNET_STATE_INACTIVE = 0
     */
    public static int lastInternetState = INTERNET_STATE_UNKNOWN;
    /**
     * Flag used to register {@link ConnectivityManager}.CONNECTIVITY_ACTION event
     * By default it is -> true
     */
    private boolean canRegisterInternetEvents = true;
    /**
     * {@link android.os.AsyncTask} used to check whether the active internet is there or not
     */
    private ActiveInternetTask activeInternetTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceMatrix.INSTANCE.initDeviceMetrics(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registers internet receiver
        registerInternetReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterInternetReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activeInternetTask != null && !activeInternetTask.isCancelled()) {
            activeInternetTask.cancel(true);
        }
    }

    /**
     * Registers ConnectivityManager.CONNECTIVITY_ACTION events
     */
    private void registerInternetReceiver() {
        //IntentFilter for registering ConnectivityManager.CONNECTIVITY_ACTION events
        IntentFilter mInternetFilter = new IntentFilter();
        mInternetFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        if (canRegisterInternetEvents) {
            registerReceiver(mInternetReceiver, mInternetFilter);
        }
    }

    /**
     * Unregisters ConnectivityManager.CONNECTIVITY_ACTION events
     */
    private void unregisterInternetReceiver() {
        if (canRegisterInternetEvents) {

            // when tried to unregister the receiver which was not registered.
            // So to handle this try-catch is used
            try {
                unregisterReceiver(mInternetReceiver);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Executes {@link ActiveInternetTask} and checks for internet connection
     */
    public void startBgInternetTask() {
        activeInternetTask = new ActiveInternetTask(this);
        activeInternetTask.execute();
    }

    /**
     * Calling this method in onCreate or onResume of child activity will stop registering for ConnectivityManager.CONNECTIVITY_ACTION events
     * It sets canRegisterInternetEvents to false
     */
    public void unregisterInternetEvents() {
        this.canRegisterInternetEvents = false;
    }

    /**
     * Called whenever there is changes in internet connection
     * Either called from onReceive of {@link BroadcastReceiver} of this base activity or from {@link ActiveInternetTask}
     *
     * @param b state of the active internet
     */
    @Override
    public void OnNetworkChanged(boolean b) {
        Logg.v(TAG, "OnNetworkChanged base:: " + b);
    }

    /**
     * Receiver to receive CONNECTIVITY_ACTION events
     */
    private BroadcastReceiver mInternetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) return;
            switch (intent.getAction()) {
                //Internet state change callback
                case ConnectivityManager.CONNECTIVITY_ACTION:
                    ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo actNetworkInfo = connMgr != null ? connMgr.getActiveNetworkInfo() : null;
                    if (actNetworkInfo != null) {
                        if (actNetworkInfo.isConnected()) {
                            //if ConstantData.lastInternetState is unknown or inactive proceed with startBgInternetTask() else calls do nothing
                            if (lastInternetState != INTERNET_STATE_ACTIVE) {
                                startBgInternetTask();
                            }
                            return;
                        }
                    }
                    lastInternetState = INTERNET_STATE_INACTIVE;
                    OnNetworkChanged(false);
                    break;
                default:
                    break;
            }
        }
    };


}