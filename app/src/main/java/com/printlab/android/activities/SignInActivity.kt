package com.printlab.android.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.futuremind.recyclerviewfastscroll.Utils
import com.printlab.android.R
import com.printlab.android.constants.AppConstants
import com.printlab.android.manager.NavigationManager
import com.printlab.android.manager.PrefsManager
import com.printlab.android.utils.ProgressLoader
import com.printlab.android.utils.Util
import com.printlab.android.view.CustomEditText
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : BaseActivity(), CustomEditText.KeyboardListener, View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        init()

    }

    private fun init() {

        field_email.init(this, this)
        field_password.init(this, this)

        forget_password.setOnClickListener(this)
        button_sign_in.setOnClickListener(this)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        field_email.SetHideKeyboard()
        field_password.SetHideKeyboard()
    }


    override fun onClick(v: View?) {

        when (v) {

            forget_password -> {
                NavigationManager.start<ForgotPasswordActivity>(this)

            }

            button_sign_in -> {

                if (validate(field_email.text.toString(), field_password.text.toString())) {
                    finish()

                    ProgressLoader.showToast(this, "Login Successfull")

                } else {
                    ProgressLoader.showToast(this, "Invalid Credentials")
                }


            }


        }

    }

    override fun onKeyboardHide(action: Int, viewId: Int) {
    }

    override fun onKeyboardShow(viewId: Int, mKeyPadHeight: Int) {
    }

    override fun onEditTouched(view: View?) {
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
    }

    override fun onBeforeTextChanged(view: View?, charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {
    }

    override fun onTextChanged(view: View?, charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {
    }

    override fun onAfterTextChanged(view: View?, editable: Editable?) {
    }


    private fun validate(email: String, password: String): Boolean {

        // Reset errors.
        email_layout.error = null
        pass_layout.error = null

        if (TextUtils.isEmpty(email)) {
            email_layout.error = "Email is required"
            return false
        } else if (!Util.isEmailValid(email)) {
            email_layout.error = "Enter a valid email"
            return false
        }

        if (TextUtils.isEmpty(password)) {
            pass_layout.error = "Password is required"
            return false
        } else if (!Util.isPasswordValid(password)) {
            pass_layout.error = "Password must contain at least 6 characters"
            return false
        }


        if (PrefsManager.exists(this, AppConstants.UserDetails.USER_NAME)) {

            if (PrefsManager.exists(this, "$email${AppConstants.UserDetails.PASSWORD}")) {

                return true
            }

        } else {

            PrefsManager.setData(this, AppConstants.UserDetails.USER_NAME, email)
            PrefsManager.setData(this, "$email${AppConstants.UserDetails.PASSWORD}", password)


        }
        return true
    }


}