package com.printlab.android.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.printlab.android.application.AppController;
import com.printlab.android.utils.Logg;

/**
 * {@link CustomEditText} is a Edittext with appropriate callbacks for onFocusChanged, onTextChanged, onKeyboardShow and
 * onKeyboardHide with functions to open, close and calculate softKeyboard height.
 */

public class CustomEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextView.OnEditorActionListener, TextWatcher {
    public static final int CLOSED_BACK = -1;
    private static final String TAG = "CustomEditText";
    private static final int OPENED = 1;
    private static final int CLOSED_ACTION = 2;
    /**
     * flag to check whether the {@link CustomEditText} focused or not
     */
    public boolean isFocused = false;
    private Activity mActivity;
    private InputMethodManager imm;
    private KeyboardListener listener;
    private View mRootView;
    private Handler handler;
    /**
     * Runnable used to calculate softKeyboard height in checkKeyboardHeight()
     */
    private Runnable mKeyboardHeightCalRunnable = new Runnable() {
        @Override
        public void run() {
            if (mRootView == null)
                return;
            Rect r = new Rect();
            mRootView.getWindowVisibleDisplayFrame(r);
            int mScreenHeight = mRootView.getRootView().getHeight();
            int mKeyPadHeight = mScreenHeight - r.bottom;
            Logg.v(TAG, "mKeyPadHeight:: " + mKeyPadHeight);
            if (listener != null) {
                listener.onKeyboardShow(getId(), mKeyPadHeight);
            }
        }
    };

    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setEditFocused(boolean mIsFocused) {
        this.isFocused = mIsFocused;
    }

    /**
     * Initialize CustomEditText with all necessary callbacks
     *
     * @param activity Activity reference to refer KeyboardListener listener
     * @param l        KeyboardListener for callback events like onKeyboardShow(), onKeyboardHide(), onEditTouched(), addTextChangedListener()
     */
    public void init(Activity activity, KeyboardListener l) {
        if (activity == null)
            return;
        try {
            this.listener = l;
        } catch (ClassCastException e) {
            throw new ClassCastException("must implement this listener");
        }

        this.mActivity = activity;

        handler = new Handler(mActivity.getMainLooper());
        // For having first letter caps for the this EditText
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        imm = (InputMethodManager) mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        mRootView = mActivity.findViewById(android.R.id.content);
        if (mRootView != null) {
            mRootView.setFocusableInTouchMode(true);
        }
        setOnFocusChangeListener(this);
        setOnEditorActionListener(this);
        addTextChangedListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Logg.v(TAG, "onFocusChange:: " + hasFocus);

        if (hasFocus) {

            if (listener != null) {
                listener.onEditTouched(v);
            }
            checkKeyboardHeight();
        }
        setEditFocused(hasFocus);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Logg.v(TAG, "onEditorAction");
        hideKeyboardLogic(actionId);
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if (null != listener) {
            Logg.v(TAG, "beforeTextChanged");
            listener.onBeforeTextChanged(this, charSequence, i, i1, i2);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (null != listener) {
            Logg.v(TAG, "onTextChanged");
            listener.onTextChanged(this, charSequence, i, i1, i2);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        Logg.v(TAG, "afterTextChanged");
        if (null != listener) {
            listener.onAfterTextChanged(this, editable);
        }
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        Logg.v(TAG, "onKeyPreIme");
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            hideKeyboardLogic(CLOSED_BACK);
            return true;
        }
        return super.onKeyPreIme(keyCode, event);
    }

    /**
     * Executes {@link CustomEditText} keyboard hiding flow
     *
     * @param actionId action such as CLOSED_BACK, CLOSED_ACTION & OPENED
     */
    public void hideKeyboardLogic(int actionId) {
        hideKeyboardLogic(actionId, true);
    }

    /**
     * Executes {@link CustomEditText} keyboard hiding flow
     *
     * @param actionId action such as CLOSED_BACK, CLOSED_ACTION & OPENED
     * @param callback flag to call interface method or not
     */
    @SuppressWarnings("SameParameterValue")
    public void hideKeyboardLogic(int actionId, boolean callback) {
        Logg.v(TAG, "hideKeyboardLogic:: " + actionId);
        if (listener != null && callback) {
            listener.onKeyboardHide(actionId, getId());
        }
        SetHideKeyboard();
        if (mActivity != null && !mActivity.isDestroyed() && mActivity.getWindow() != null && mActivity.getWindow().getDecorView() != null)
            AppController.Companion.getInstance().hideSystemUI(mActivity.getWindow().getDecorView());
        if (mRootView != null) {
            mRootView.requestFocus();
        }
        isFocused = false;
    }

    /**
     * Hide soft keyboard
     */
    public void SetHideKeyboard() {
        if (mActivity == null || imm == null || mActivity.getCurrentFocus() == null)
            return;
        imm.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Show soft keyboard
     */
    public void SetShowKeyboard() {
        if (imm != null)
            imm.showSoftInput(this, 0);
    }

    /**
     * Calculates keyboard height and triggers {@link KeyboardListener}.onKeyboardShow callback
     */
    private void checkKeyboardHeight() {
        if (handler != null) {
            handler.removeCallbacks(mKeyboardHeightCalRunnable);
        }
        if (mActivity != null && handler != null) {
            handler.postDelayed(mKeyboardHeightCalRunnable, 500);
        }
    }

    /**
     * Clear views and listeners
     */
    protected void destroy() {
        mActivity = null;
        listener = null;
        imm = null;
    }

    /**
     * {@link KeyboardListener} is an common interface to trigger different events changes
     * such as onFocusChanged, onTextChanged, onKeyboardShow and onKeyboardHide.
     */
    public interface KeyboardListener {

        void onKeyboardHide(int action, int viewId);

        void onKeyboardShow(int viewId, int mKeyPadHeight);

        void onEditTouched(View view);

        void onFocusChange(View view, boolean hasFocus);

        void onBeforeTextChanged(View view, CharSequence charSequence, int i, int i1, int i2);

        void onTextChanged(View view, CharSequence charSequence, int i, int i1, int i2);

        void onAfterTextChanged(View view, Editable editable);
    }
}