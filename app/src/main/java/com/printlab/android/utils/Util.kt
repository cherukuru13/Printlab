package com.printlab.android.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.support.v4.content.FileProvider
import android.widget.Toast
import java.io.File
import java.text.DecimalFormat

object Util {

    fun fileSize(size: Long): String {
        if (size <= 0)
            return "0 KB"

        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()

        return DecimalFormat("#,##0.#").format(size / Math.pow(1024.0, digitGroups.toDouble())) + " " + units[digitGroups]
    }

    fun duration(sec: Long): String {
        val hours = sec / 3600
        val minutes = (sec % 3600) / 60
        val seconds = sec % 60

        return when {
            hours > 0 -> {
                var str = String.format("%02d hr", hours)
                if (minutes > 0) {
                    str += String.format(" %02d min", minutes)
                }
                if (seconds > 0) {
                    str += String.format(" %02d sec", seconds)
                }
                str
            }
            hours == 0L && minutes > 0 -> {
                var str = String.format(" %02d min", minutes)
                if (seconds > 0) {
                    str += String.format(" %02d sec", seconds)
                }
                str
            }
            else -> String.format("%02d sec", seconds)
        }
    }

    fun durationWithSeconds(sec: Long): String {
        val hours = sec / 3600
        val minutes = (sec % 3600) / 60
        val seconds = sec % 60

        return when {
            hours > 0 -> {
                var str = String.format("%02d hr", hours)
                if (minutes > 0) {
                    str += String.format(" %02d min", minutes)
                }
                if (seconds > 0) {
                    str += String.format(" %02d sec", seconds)
                }
                str
            }
            hours == 0L && minutes > 0 -> {
                var str = String.format(" %02d min", minutes)
                if (seconds > 0) {
                    str += String.format(" %02d sec", seconds)
                }
                str
            }
            else -> String.format("%02d sec", seconds)
        }
    }

   /* fun checkInternetConnection(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }*/


}