package com.printlab.android.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.printlab.android.R
import com.printlab.android.manager.NavigationManager
import kotlinx.android.synthetic.main.activity_forgot_password.*


class ForgotPasswordActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)


        sent.setOnClickListener {
            val emailId = field_email.text.toString()
            resetUserPassword(emailId)
        }

    }

    private fun resetUserPassword(email: String) {
        if (!validateForm()) {
            return
        } else {

            val progressDialog = ProgressDialog(this@ForgotPasswordActivity)
            progressDialog.setMessage("verifying..")
            progressDialog.show()


            NavigationManager.start<SignInActivity>(this)


        }
    }

    private fun validateForm(): Boolean {
        var result = true
        if (TextUtils.isEmpty(field_email.text.toString())) {
            field_email.error = "Required"
            result = false
        } else {
            field_email.error = null
        }

        return result
    }
}
