package com.printlab.android.task;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Keep;
import com.printlab.android.activities.BaseActivity;
import com.printlab.android.utils.Logg;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by chuchutv on 15/3/18.
 * {@link ActiveInternetTask} used to check whether active internet there or not by sending small data to google.com using {@link HttpURLConnection}
 */
@Keep
public class ActiveInternetTask extends AsyncTask<Void, Void, Boolean> {

    private static final String TAG = "ActiveInternetTask";
    private Callback mCallback;

    public ActiveInternetTask(Context context) {
        try {
            this.mCallback = (Callback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Should implement listener ActiveInternetTask.Callback");
        }
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {

            HttpURLConnection urlCon = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
            urlCon.setRequestProperty("User-Agent", "Test");
            urlCon.setRequestProperty("Connection", "close");
            urlCon.setConnectTimeout(10000);
            urlCon.connect();
            return (urlCon.getResponseCode() == 200);

        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        Logg.v(TAG, "ActiveInternetTask isCancelled -> " + isCancelled());

        BaseActivity.Companion.setLastInternetState(aBoolean ? BaseActivity.Companion.getINTERNET_STATE_ACTIVE() : BaseActivity.Companion.getINTERNET_STATE_INACTIVE());
        if (!isCancelled()) mCallback.OnNetworkChanged(aBoolean);
    }

    /**
     * {@code interface} contains implementation methods for connectivity change events
     */
    public interface Callback {
        void OnNetworkChanged(boolean b);
    }
}