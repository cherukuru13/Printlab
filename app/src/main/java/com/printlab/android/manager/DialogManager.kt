package com.cmnbk.madhuramshop.manager

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.widget.TextView
import com.printlab.android.R

object DialogManager {
    const val TAG = "DialogManager"

    fun dialog(activity: Activity, title: String?, msg: String, callback: Callback? = null) {
        alert(
            activity, "", title, msg, okBtn = activity.getString(android.R.string.ok),
            cancellable = false, callback = callback
        )
    }

    fun okDialog(
        context: Activity, tag: String = TAG,
        title: String = "", msg: String,
        callback: Callback?
    ) {
        alert(
            context, tag, title, msg, okBtn = context.getString(android.R.string.ok),
            cancellable = false, callback = callback
        )
    }

    fun okCancelDialog(
        context: Activity, tag: String = TAG,
        title: String, msg: String,
        callback: Callback?
    ) {
        alert(
            context, tag, title, msg, okBtn = context.getString(android.R.string.ok),
            cancelBtn = context.getString(android.R.string.cancel),
            callback = callback
        )
    }

    fun okCancelDialog(
        context: Activity, tag: String = TAG,
        title: String, msg: String,
        okBtn: String, cancelBtn: String,
        callback: Callback?
    ) {
        alert(
            context, tag, title, msg, okBtn = okBtn,
            cancelBtn = cancelBtn,
            callback = callback
        )
    }

    private fun alert(
        activity: Activity, tag: String = TAG,
        title: String? = "", msg: String,
        cancellable: Boolean = true,
        okBtn: String,
        cancelBtn: String? = "",
        callback: Callback?
    ) {
        val dlg = Dialog(activity, R.style.MyAlertDialog)
        dlg.setContentView(R.layout.view_alert_dialog)
        dlg.apply {
            setCancelable(cancellable)

            findViewById<TextView>(R.id.title).text = title
            findViewById<TextView>(R.id.message).text = msg
            findViewById<TextView>(R.id.okBtn).text = okBtn
            findViewById<TextView>(R.id.cancelBtn).text = cancelBtn

            if (title.isNullOrEmpty())
                findViewById<TextView>(R.id.title).visibility = View.GONE
            if (cancelBtn.isNullOrEmpty())
                findViewById<TextView>(R.id.cancelBtn).visibility = View.GONE

            findViewById<TextView>(R.id.okBtn).setOnClickListener {
                dismiss()
                callback?.onDialogSuccess(tag)
            }
            findViewById<TextView>(R.id.cancelBtn).setOnClickListener {
                dismiss()
                callback?.onDialogCancel(tag)
            }
            show()
        }
    }

    interface Callback {
        fun onDialogSuccess(tag: String)
        fun onDialogCancel(tag: String)
    }

}