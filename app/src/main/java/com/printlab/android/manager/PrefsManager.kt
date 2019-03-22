package com.printlab.android.manager

import android.content.Context
import android.content.SharedPreferences
import com.printlab.android.constants.AppConstants

import com.printlab.android.utils.Logg

object PrefsManager {
    private var prefs: SharedPreferences? = null

    private fun getPref(context: Context): SharedPreferences? {
        if (prefs == null)
            prefs = context.getSharedPreferences(AppConstants.Key.PREF_FILE, Context.MODE_PRIVATE)
        return prefs
    }

    fun setData(context: Context, key: String, value: Any) {
        Logg.d("PrefsManager", "setData:: $value")
        val editor = getPref(context)?.edit()

        when (value) {
            is String -> editor?.putString(key, value)
            is Int -> editor?.putInt(key, value)
            is Long -> editor?.putLong(key, value)
            is Float -> editor?.putFloat(key, value)
            is Boolean -> editor?.putBoolean(key, value)
        }
        editor?.apply()
    }

    fun getData(context: Context, key: String, defValue: String): String {
        Logg.d("PrefsManager", "prefs:: ${prefs == null}")
        Logg.d("PrefsManager", "prefs:: ${prefs?.contains(key)}")
        Logg.d("PrefsManager", "prefs:: ${prefs?.getString(key, defValue)}")
        return getPref(context)?.getString(key, defValue) ?: defValue
    }

    fun getData(context: Context, key: String, defValue: Int): Int {
        return getPref(context)?.getInt(key, defValue) ?: defValue
    }

    fun getData(context: Context, key: String, defValue: Long): Long {
        return getPref(context)?.getLong(key, defValue) ?: defValue
    }

    fun getData(context: Context, key: String, defValue: Float): Float {
        return getPref(context)?.getFloat(key, defValue) ?: defValue
    }

    fun getData(context: Context, key: String, defValue: Boolean): Boolean {
        return getPref(context)?.getBoolean(key, defValue) ?: defValue
    }

    fun exists(context: Context, key: String) = getPref(context)?.contains(key) ?: false

    fun remove(context: Context, key: String) = {
        getPref(context)?.edit()?.remove(key)?.apply()
    }

}