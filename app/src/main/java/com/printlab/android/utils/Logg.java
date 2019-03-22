package com.printlab.android.utils;

import android.util.Log;



public class Logg {

    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }
    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }
    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }
    public static void time(String tag, String msg) {
        Log.v(tag, msg + " << " + System.currentTimeMillis());
    }
}
