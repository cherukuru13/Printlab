package com.printlab.android.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.pnikosis.materialishprogress.ProgressWheel
import com.printlab.android.R

object ProgressLoader {

    private var mProgressDialog: ProgressDialog? = null

    fun showProgressDialog(context: Context) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(context)
            mProgressDialog!!.setCancelable(false)
            mProgressDialog!!.setMessage("Loading...")
        }

        mProgressDialog!!.show()
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }


    fun showSnackBar(view:View){

        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()

    }
    fun showLoader(context: Context, mView: ViewGroup, hide: Boolean) {

        val mProgressWheel = ProgressWheel(context)

        DesignManager.initParams(
            mProgressWheel,
            (DeviceMatrix.width * 0.05).toInt(),
            (DeviceMatrix.width * 0.05).toInt()
        )

        mProgressWheel.barColor = ContextCompat.getColor(context, R.color.skyblue)
        mProgressWheel.barWidth = (DeviceMatrix.width * 0.05).toInt()
        mProgressWheel.circleRadius = (DeviceMatrix.width * 0.05).toInt()
        mView.addView(mProgressWheel)
        if (hide) {
            mProgressWheel.visibility = View.GONE
        } else {
            mProgressWheel.visibility = View.VISIBLE

        }

    }

    fun showToast(context: Context, message: String = "") {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }

    fun disableTouch(context: Activity) {

        context.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun enableTouch(context: Activity) {

        context.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

    }


}