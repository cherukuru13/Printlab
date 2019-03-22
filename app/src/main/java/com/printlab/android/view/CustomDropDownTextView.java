package com.printlab.android.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;
import com.cmnbk.madhuramshop.manager.DesignManager;
import com.printlab.android.R;
import com.printlab.android.decoration.DividerItemDecorator;
import com.printlab.android.utils.DrawableUtil;
import com.printlab.android.utils.Logg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ArunShankar on 11/4/18. {@link CustomDropDownTextView} is a TextView which extends {@link TextView},
 * used to show dropdown based upon the {@link ArrayList} passed to it and you can also pass alias values to the
 * list text with {@link HashMap} acting upon it. You have helper methods to change background properties of this view
 */
@SuppressWarnings("unused")
public class CustomDropDownTextView extends RelativeLayout implements View.OnClickListener, PopupWindow.OnDismissListener, View.OnTouchListener {
    private final String TAG = "CustomDropDownTextView";
    /**
     * Selected text of this {@link CustomDropDownTextView}
     */
    private TextView mDDTxt;
    /**
     * Indicator for this {@link CustomDropDownTextView}
     */
    private ImageView mDDIndicator;
    /**
     * Drawable for the indicator
     */
    private Drawable mIndicator;
    /**
     * Color of the drawable for indicator
     */
    @ColorInt
    private int mIndicatorColor = Integer.MIN_VALUE;
    /**
     * Text color of this {@link CustomDropDownTextView}
     */
    @ColorInt
    private int mTxtColor = Integer.MIN_VALUE;
    /**
     * Background color for both this view and dropdown, by default it is -1
     */
    @ColorInt
    private int mBgColor = Integer.MIN_VALUE;
    /**
     * Stroke width of both this view and dropdown, by default it is 0
     */
    private int mStrokeSize = 0;
    /**
     * Stroke color of both this view and dropdown, by default it is -1
     */
    @ColorInt
    private int mStrokeColor = Integer.MIN_VALUE;
    /**
     * Corner radius of both this view and dropdown, by default it is 0
     */
    private int mCornerRadius = 0;
    /**
     * {@link PopupWindow} acts as a dropdown list to be shown when clicking on this {@link CustomDropDownTextView}
     */
    private PopupWindow mPopupWindow;
    /**
     * Flag to show dropdown for the first time, By default it is false
     */
    private boolean mDDShowByDefault = false;
    /**
     * Boundaries of this {@link CustomDropDownTextView}, which is used in popup
     * {@code setTouchInterceptor()} to disable dismissing popup, when touching this {@link CustomDropDownTextView}
     */
    private RectF mViewRect;
    /**
     * Dropdown recycler view
     */
    private RecyclerView mPopupRecyclerView;
    /**
     * Dropdown root view
     */
    private LinearLayout mPopupRootView;
    /**
     * Dropdown recycler adapter
     */
    private DDListAdapter mAdapter;
    /**
     * List of values to be shown in the dropdown, this contains selected item also
     */
    private ArrayList<String> l = new ArrayList<>();
    /**
     * Flag to set max items to be shown in dropdown as per height wise,
     * if {@code mMaxItemsToShow} is greater than dropdown size, the height of the dropdown
     * simply set to {@code ViewGroup.LayoutParams.WRAP_CONTENT}
     */
    private int mMaxItemsToShow = -1;
    /**
     * Height of this item in the dropdown, if not passed it takes the height of this {@link CustomDropDownTextView}.
     */
    private int mItemHeight = 0;
    /**
     * Background color of the dropdown item by default
     */
    @ColorInt
    private int mItemBgColor = Integer.MIN_VALUE;
    /**
     * Background color of the dropdown item when selected
     */
    @ColorInt
    private int mItemBgSelColor = Integer.MIN_VALUE;
    /**
     * Highlight color of the dropdown item when pressed
     */
    @ColorInt
    private int mItemHighlightColor = Integer.MIN_VALUE;
    /**
     * Text color of the dropdown item by default
     */
    @ColorInt
    private int mItemTextColor = Integer.MIN_VALUE;
    /**
     * Text color of the dropdown item when selected
     */
    @ColorInt
    private int mItemTextSelColor = Integer.MIN_VALUE;
    /**
     * Text color of the dropdown item when pressed
     */
    @ColorInt
    private int mItemTextHighlightColor = Integer.MIN_VALUE;
    /**
     * Divider color of the dropdown list
     */
    @ColorInt
    private int mDividerColor = Integer.MIN_VALUE;
    /**
     * Divider height of the dropdown list
     */
    private int mDividerHeight = 0;
    /**
     * Text size of the item, if this value is not passed it takes the size of this {@link CustomDropDownTextView}
     */
    private int mItemTextSize = -1;
    /**
     * Left padding of the dropdown item
     */
    private int mItemPadding = 0;
    /**
     * View id of this {@link CustomDropDownTextView}
     */
    private int viewId = -1;
    /**
     * Listener for this {@link CustomDropDownTextView} when there is a changes in the state such as
     * {@code onStateChange} when clicked on this {@link CustomDropDownTextView} and
     * {@code onItemChanged} when item clicked in the dropdown
     */
    private Callback callback;
    /**
     * Current selected item position if this {@link CustomDropDownTextView}
     */
    private int mItemActivePos = 0;
    /**
     * Flag to show/not-show dropdown when clicking on this {@link CustomDropDownTextView}.
     * By default it is {@code true}
     */
    private boolean mCanShowDDOnTapped = true;
    /**
     * Flag to show/hide selected item from dropdown. By default it is true.
     */
    private boolean mShowSelectedItem = true;
    /**
     * By default  selected language pos  is set to -1.
     */
    private int mPopUpWidth = 0;

    /**
     * This map is used for alternate texts to be shown for some item
     */
    public CustomDropDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initView();
        initViewRect();
    }

    /**
     * Initializing parameters which are passed as a view parameters through xml
     *
     * @param attrs all {@code AttributeSet} of this view attached through xml
     */
    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomDropDownTextView, 0, 0);
        if (typedArray != null) {
            mIndicator = typedArray.getDrawable(R.styleable.CustomDropDownTextView_dd_indicatorResId);
            mIndicatorColor = typedArray.getColor(R.styleable.CustomDropDownTextView_dd_indicatorColor, mIndicatorColor);

            mTxtColor = typedArray.getColor(R.styleable.CustomDropDownTextView_dd_textColor, mTxtColor);
            mBgColor = typedArray.getColor(R.styleable.CustomDropDownTextView_dd_backgroundColor, mBgColor);
            mStrokeSize = typedArray.getDimensionPixelSize(R.styleable.CustomDropDownTextView_dd_strokeWidth, mStrokeSize);
            mStrokeColor = typedArray.getColor(R.styleable.CustomDropDownTextView_dd_strokeColor, mStrokeColor);
            mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.CustomDropDownTextView_dd_cornerRadius, mCornerRadius);

            mMaxItemsToShow = typedArray.getInteger(R.styleable.CustomDropDownTextView_dd_maxItemsToShow, mMaxItemsToShow);

            mDDShowByDefault = typedArray.getBoolean(R.styleable.CustomDropDownTextView_dd_showByDefault, mDDShowByDefault);

            mItemHeight = typedArray.getDimensionPixelSize(R.styleable.CustomDropDownTextView_dd_itemHeight, mItemHeight);
            mItemBgColor = typedArray.getColor(R.styleable.CustomDropDownTextView_dd_itemBgColor, mItemBgColor);
            mItemBgSelColor = typedArray.getColor(R.styleable.CustomDropDownTextView_dd_itemBgSelColor, mItemBgSelColor);
            mItemHighlightColor = typedArray.getColor(R.styleable.CustomDropDownTextView_dd_itemHighlightColor, mItemHighlightColor);
            mItemTextColor = typedArray.getColor(R.styleable.CustomDropDownTextView_dd_itemTextColor, mItemTextColor);
            mItemTextSelColor = typedArray.getColor(R.styleable.CustomDropDownTextView_dd_itemTextSelColor, mItemTextSelColor);
            mItemTextHighlightColor = typedArray.getColor(R.styleable.CustomDropDownTextView_dd_itemTextHighlightColor, mItemTextHighlightColor);
            mDividerColor = typedArray.getColor(R.styleable.CustomDropDownTextView_dd_dividerColor, mDividerColor);
            mDividerHeight = typedArray.getDimensionPixelSize(R.styleable.CustomDropDownTextView_dd_dividerHeight, mDividerHeight);
            mItemTextSize = typedArray.getDimensionPixelSize(R.styleable.CustomDropDownTextView_dd_itemTextSize, mItemTextSize);
            mItemPadding = typedArray.getDimensionPixelSize(R.styleable.CustomDropDownTextView_dd_itemPadding, mItemPadding);
            mShowSelectedItem = typedArray.getBoolean(R.styleable.CustomDropDownTextView_dd_showSelectedItem, mShowSelectedItem);

            typedArray.recycle();
        }
    }

    /**
     * One time initialization of this view
     */
    private void initView() {
        View view = inflate(getContext(), R.layout.view_dropdown, this);
        mDDTxt = view.findViewById(R.id.selectedTxt);
        mDDIndicator = view.findViewById(R.id.indicator);
        if (mDDTxt != null) {
            mDDTxt.setMaxLines(1);
            mDDTxt.setEllipsize(TextUtils.TruncateAt.END);
            mDDTxt.setTextColor((mTxtColor != Integer.MIN_VALUE) ? mTxtColor : ContextCompat.getColor(getContext(), android.R.color.darker_gray));
        }
        setOnClickListener(this);
        setGravity(Gravity.CENTER_VERTICAL);
        viewId = getId();
        refreshIndicator();
        drawViewBackground(mDDShowByDefault);
        setLayoutTransition(new LayoutTransition());
    }

    /**
     * Initialize view boundaries to {@code mViewRect}, which is used when
     * {@code mPopupWindow.setTouchInterceptor()}, to disable dismiss when clicked
     * on this {@link CustomDropDownTextView}
     */
    private void initViewRect() {
        post(new Runnable() {
            @Override
            public void run() {
                int viewPos[] = new int[2];
                getLocationOnScreen(viewPos);
                mViewRect = new RectF(viewPos[0], viewPos[1], viewPos[0] + getWidth(), viewPos[1] + getHeight());
            }
        });
    }

    /**
     * Get selected text from the dropdown
     *
     * @return selected text from the dropdown
     */
    public String getDDTxt() {
        return mDDTxt.getText().toString();
    }

    /**
     * Gets selected item position
     *
     * @return selected item position
     */
    public int getDDPosition() {
        return mItemActivePos;
    }

    /**
     * Initialization block of this dropdown, this sets recycler height, item height and initializes {@code PopupWindow}
     */
    private void initDD() {
        if (mPopupWindow != null) {
            mPopupWindow = null;
        }
        if (mPopupRootView != null) {
            mPopupRootView.removeAllViews();
            mPopupRootView = null;
        }
        if (mPopupRecyclerView != null) {
            mPopupRecyclerView = null;
        }
        post(new Runnable() {
            @Override
            public void run() {
                if (mItemHeight == 0) {
                    mItemHeight = getHeight();
                }

                mAdapter = new DDListAdapter();
                int popupHeight = (mMaxItemsToShow == -1) ? ViewGroup.LayoutParams.WRAP_CONTENT : (l.size() > mMaxItemsToShow) ? ((mMaxItemsToShow * mItemHeight) + mStrokeSize + (mCornerRadius / 2)) : ((l.size() * mItemHeight) + mStrokeSize + (mCornerRadius / 2));
                mPopupRootView = new LinearLayout(getContext());
                mPopupRecyclerView = new RecyclerView(getContext());
                mPopupRootView.addView(mPopupRecyclerView);

                LinearLayout.LayoutParams mPopupViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mPopupRootView.setLayoutParams(mPopupViewParams);
                LinearLayout.LayoutParams mRecyclerViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mRecyclerViewParams.leftMargin = 0;
                mRecyclerViewParams.topMargin = mCornerRadius;
                mRecyclerViewParams.rightMargin = 0;
                mRecyclerViewParams.bottomMargin = mCornerRadius;
                mPopupRecyclerView.setLayoutParams(mRecyclerViewParams);
                mPopupRecyclerView.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
                Logg.v(TAG, "width\t" + getWidth());
                if (mPopUpWidth == 0) {
                    mPopUpWidth = getWidth();
                }
                Logg.v(TAG, "popupHeight\t" + popupHeight);
                mPopupWindow = new PopupWindow(mPopupRootView, getWidth(), popupHeight);
                mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mPopupWindow.setOutsideTouchable(true);
                mPopupWindow.setTouchInterceptor(CustomDropDownTextView.this);
                mPopupWindow.setOnDismissListener(CustomDropDownTextView.this);
                drawDDBackground();
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                mPopupRecyclerView.setLayoutManager(llm);
                //set divider for the recycler item's only when (mDividerColor != -1 && mDividerHeight != 0) conditions met
                if (mDividerColor != Integer.MIN_VALUE && mDividerHeight != 0) {
                    DividerItemDecorator divItemDecor = new DividerItemDecorator(getContext(), DividerItemDecoration.VERTICAL);
                    divItemDecor.setDrawable(DrawableUtil.INSTANCE.drawable(mDividerHeight, mDividerColor));
                    mPopupRecyclerView.addItemDecoration(divItemDecor);
                }
                mPopupRecyclerView.setAdapter(mAdapter);
                if (mDDShowByDefault) {
                    showDD();
                    mDDShowByDefault = false;
//                    if (mDDWrapAnimate) wrapAnimate(true);
                } else {
//                    if (mDDWrapAnimate) wrapAnimate(true);
                }
            }
        });
    }

    /**
     * Method to re-apply background for {@code RecyclerView}
     */
    private void drawDDBackground() {
        if (mPopupRecyclerView != null) {
            mPopupRootView.setBackground(DrawableUtil.INSTANCE.draw(mItemBgColor, mStrokeSize, mStrokeColor,
                    mCornerRadius));
            mPopupRootView.setTranslationY(-mCornerRadius);
        }
    }

    /**
     * Method to re-apply background for {@code {@link CustomDropDownTextView }}
     *
     * @param isDDOpened current state of this {@link CustomDropDownTextView}
     */
    private void drawViewBackground(boolean isDDOpened) {
        if (isDDOpened) {
            setBackground(DrawableUtil.INSTANCE.draw(mBgColor, mStrokeSize, mStrokeColor, new float[]{mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, 0, 0, 0, 0}));
        } else {
            setBackground(DrawableUtil.INSTANCE.draw(mBgColor, mStrokeSize, mStrokeColor, mCornerRadius));
        }
        drawDDBackground();
    }

    /**
     * Initialize callback block of this {@link CustomDropDownTextView}
     *
     * @param callback instance of this interface
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    /**
     * Set indicator drawable programatically
     *
     * @param mIndicator drawable used as a dropdown indicator
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setIndicator(Drawable mIndicator) {
        this.mIndicator = mIndicator;
        return this;
    }

    /**
     * Change indicator color of this {@link CustomDropDownTextView}
     *
     * @param mIndicatorColor resource id of the color
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setIndicatorColor(@ColorInt int mIndicatorColor) {
        this.mIndicatorColor = mIndicatorColor;
        return this;
    }

    /**
     * Method to resize indicator size
     *
     * @param size width and height of the indicator
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView resizeIndicator(int size) {
        if (mIndicator != null) {
            Bitmap bitmap = ((BitmapDrawable) mIndicator).getBitmap();
            mIndicator = null;
            mIndicator = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, size, size, true));
        }
        return this;
    }

    /**
     * Method to set bg color programmatically
     *
     * @param mBackgroundColor color resource id for this background
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setBgColor(@ColorInt int mBackgroundColor) {
        this.mBgColor = mBackgroundColor;
        return this;
    }

    /**
     * Method to set text color of this {@link CustomDropDownTextView}
     *
     * @param mTxtColor color resource id
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setTextColor(@ColorInt int mTxtColor) {
        this.mTxtColor = mTxtColor;
        if (mDDTxt != null) mDDTxt.setTextColor(mTxtColor);
        return this;
    }

    /**
     * Sets {@code height} of the item in the dropdown
     *
     * @param mItemHeight {@code height} of the item
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setItemHeight(int mItemHeight) {
        this.mItemHeight = mItemHeight;
        return this;
    }

    /**
     * Sets background color of the item in the dropdown
     *
     * @param mItemBgColor {@code backgroundColor} of the item
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setItemBgColor(@ColorInt int mItemBgColor) {
        this.mItemBgColor = mItemBgColor;
        return this;
    }

    /**
     * Sets background color of the selected item in the dropdown
     *
     * @param mItemBgSelColor {@code backgroundColor} of the selected item
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setItemBgSelColor(@ColorInt int mItemBgSelColor) {
        this.mItemBgSelColor = mItemBgSelColor;
        return this;
    }

    /**
     * Sets highlight color of the selected item in the dropdown
     *
     * @param mItemHighlightColor color of the item when pressed
     */
    public CustomDropDownTextView setItemHighlightColor(@ColorInt int mItemHighlightColor) {
        this.mItemHighlightColor = mItemHighlightColor;
        return this;
    }

    /**
     * Sets text color of the item in the dropdown
     *
     * @param mItemTextColor {@code color} of the item
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setItemTextColor(@ColorInt int mItemTextColor) {
        this.mItemTextColor = mItemTextColor;
        return this;
    }

    /**
     * Sets text color of the selected item in the dropdown
     *
     * @param mItemTextSelColor {@code color} of the selected item
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setItemTextSelColor(@ColorInt int mItemTextSelColor) {
        this.mItemTextSelColor = mItemTextSelColor;
        return this;
    }

    /**
     * Set divider color of the dropdown list
     *
     * @param mDividerColor color resource Id
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setDividerColor(@ColorInt int mDividerColor) {
        this.mDividerColor = mDividerColor;
        if (mPopupRecyclerView != null) {
            DividerItemDecorator decorator = (DividerItemDecorator) mPopupRecyclerView.getItemDecorationAt(0);
            if (decorator != null)
                decorator.setDrawable(DrawableUtil.INSTANCE.drawable(1, mDividerColor));
        }
        return this;
    }

    /**
     * Set divider height of the dropdown list
     *
     * @param mDividerHeight divider height
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setDividerHeight(int mDividerHeight) {
        this.mDividerHeight = mDividerHeight;
        return this;
    }

    /**
     * Sets stroke width of the this {@link CustomDropDownTextView} and dropdown
     *
     * @param mStrokeSize stroke width
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setStrokeWidth(int mStrokeSize) {
        this.mStrokeSize = mStrokeSize;
        return this;
    }

    /**
     * Sets stroke color of the this {@link CustomDropDownTextView} and dropdown
     *
     * @param mStrokeColor stroke color
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setStrokeColor(@ColorInt int mStrokeColor) {
        this.mStrokeColor = mStrokeColor;
        return this;
    }

    /**
     * Sets corner color of the this {@link CustomDropDownTextView} and dropdown
     *
     * @param mCornerRadius corner color
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setCornerRadius(int mCornerRadius) {
        this.mCornerRadius = mCornerRadius;
        return this;
    }

    /**
     * Sets text size of this {@link CustomDropDownTextView}
     *
     * @param unit dp, sp or px
     * @param size size of the text
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setViewTextSize(int unit, float size) {
        mDDTxt.setTextSize(unit, size);
        return this;
    }

    /**
     * Sets text size of the dropdown item
     *
     * @param mItemTextSize size of the text
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setItemTextSize(int mItemTextSize) {
        this.mItemTextSize = mItemTextSize;
        return this;
    }

    /**
     * Sets padding for this {@link CustomDropDownTextView},it sets padding based on corner radius size
     *
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setViewPadding() {
        super.setPadding(mItemPadding / 2, 0, mItemPadding, 0);
        return this;
    }

    /**
     * Sets padding left of this dropdown item
     *
     * @param mItemPadding padding left
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setItemPadding(int mItemPadding) {
        this.mItemPadding = mItemPadding;
        return this;
    }

    /**
     * Returns that when the {@link CustomDropDownTextView} clicks enabled or not.
     *
     * @return mCanShowDDOnTapped
     */
    public boolean canShowDDOnTapped() {
        return mCanShowDDOnTapped;
    }

    /**
     * Set flag to show/not-show dropdown when clicking on this {@link CustomDropDownTextView}.
     *
     * @param mCanShowDD {@code true} - click enabled, {@code false} - click disabled.
     */
    public void setShowDDOnTapped(boolean mCanShowDD) {
        this.mCanShowDDOnTapped = mCanShowDD;
    }

    /**
     * Sets placeholder text.
     *
     * @param hint text to be applied to placeholder
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setHint(String hint) {
        mDDTxt.setHint(hint);
        return this;
    }

    /**
     * Sets selected item of the dropdown programmatically
     * Call {@code refresh()} after this method to update selected text in the list
     *
     * @param txtActivePos item position to be selected
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setTextAtPos(int txtActivePos) {
        mItemActivePos = txtActivePos;
        if (txtActivePos > -1 && l != null && l.size() - 1 >= txtActivePos)
            mDDTxt.setText(l.get(txtActivePos));
        else
            mDDTxt.setText("");
        return this;
    }

    /**
     * Sets dropdown list of this {@link CustomDropDownTextView}
     *
     * @param list         It uses {@code ArrayList<String>} as a data-set for this dropdown
     * @param txtActivePos to select active dropdown position
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setDDList(ArrayList<String> list, int txtActivePos) {
        if (list == null) return this;
        if (l == null) l = new ArrayList<>();
        l.clear();
        l.addAll(list);
        initDD();
        if (mDDIndicator != null)
            mDDIndicator.setVisibility(l.size() > 0 ? View.VISIBLE : View.INVISIBLE);

        mDDTxt.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        setTextAtPos(txtActivePos).refresh();
        return this;
    }

    /**
     * Sets dropdown list of this {@link CustomDropDownTextView}
     *
     * @param list It uses {@code ArrayList<String>} as a data-set for this dropdown
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView setDDList(ArrayList<String> list) {
        setDDList(list, mItemActivePos);
        return this;
    }

    /**
     * Refreshes the {@link DDListAdapter}, simply calls {@code notifyDataSetChanged()}
     */
    public void refresh() {
        if (mAdapter != null) mAdapter.notifyDataSetChanged();
    }

    /**
     * Method to refresh background programatically
     *
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView refreshBg() {
        if (isDDVisible()) {
            setBackground(DrawableUtil.INSTANCE.draw(mBgColor, mStrokeSize, mStrokeColor, new float[]{mCornerRadius, mCornerRadius, mCornerRadius, mCornerRadius, 0, 0, 0, 0}));
        } else {
            setBackground(DrawableUtil.INSTANCE.draw(mBgColor, mStrokeSize, mStrokeColor, mCornerRadius));
        }
        drawDDBackground();
        return this;
    }

    /**
     * Method to re-apply indicator for this {@link CustomDropDownTextView}, should be used after
     * you change {@code mIndicator} drawable or {@code mIndicatorColor} programmatically
     *
     * @return {@link CustomDropDownTextView}
     */
    public CustomDropDownTextView refreshIndicator() {
        if (mDDIndicator == null) return this;
        if (mIndicatorColor != Integer.MIN_VALUE) {
            mDDIndicator.setColorFilter(mIndicatorColor, PorterDuff.Mode.SRC_IN);
        }
        if (mIndicator != null) {
            mDDIndicator.setImageDrawable(mIndicator);
        }
        return this;
    }

    /**
     * Dismisses the dropdown list if shows
     */
    public void dismissDD() {
        animateDDIndicator(true);
        if (isDDVisible()) {
            mPopupWindow.dismiss();
        }
        drawViewBackground(false);
        if (callback != null) {
            callback.onStateChange(viewId, false);
        }
    }

    /**
     * Shows the dropdown list
     */
    public void showDD() {
        animateDDIndicator(false);
        if ((null != mPopupWindow) && !isDDVisible()) {
            mPopupWindow.showAsDropDown(this);
        }
        drawViewBackground(true);
        if (callback != null) {
            callback.onStateChange(viewId, true);
        }
    }

    /**
     * It toggles between {@code showDD()} and {@code dismissDD()}
     */
    private void toggleDD() {
        if (l == null || l.isEmpty()) return;
        if (isDDVisible()) {
            dismissDD();
        } else {
            showDD();
        }
    }

    /**
     * Used to check whether dropdown is visible or not.
     *
     * @return {@code true} - if dropdown is visible else {@code false} - if not visible.
     */
    public boolean isDDVisible() {
        return (mPopupWindow != null && mPopupWindow.isShowing());
    }

    @Override
    public void onClick(View v) {
        if (callback != null) callback.onTapped(viewId);

        if (mCanShowDDOnTapped) {
            toggleDD();
        }
    }

    @Override
    public void onDismiss() {
        dismissDD();
    }

    /**
     * Intercepted touch event for {@link PopupWindow}, used to disable dismissing popup view,
     * when touched in the boundaries of this {@link CustomDropDownTextView}
     *
     * @param v     popup touched
     * @param event event triggered in the popup
     * @return {@code true} -> we have to handle popup dismiss functionalitym, {@code false} -> dismisses by default
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            if (mViewRect.left <= x && mViewRect.right >= x && mViewRect.top <= y && mViewRect.bottom >= y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Executing rotate animation for this {@link CustomDropDownTextView} {@code mDDIndicator}. It
     * clears rotation flag after each complete cycle
     *
     * @param popupClosing if popup closes set {@code 360} for rotation otherwise {@code 180}
     */
    private void animateDDIndicator(final boolean popupClosing) {
        if (mDDIndicator != null) {
            mDDIndicator.animate().cancel();
            mDDIndicator.animate().rotation(!popupClosing ? 180 : 360).setDuration(getResources().getInteger(android.R.integer.config_mediumAnimTime)).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
//                    if (mDDWrapAnimate) wrapAnimate(popupClosing);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (popupClosing) mDDIndicator.setRotation(0);
                }
            });
        }
    }

    public View getRecycler() {
        return mPopupRecyclerView;
    }

    /**
     * Callback for the event changes of this {@link CustomDropDownTextView}
     */
    public interface Callback {
        void onTapped(int viewId);

        void onStateChange(int viewId, boolean b);

        void onItemChanged(int viewId, int pos, String s);
    }

    /**
     * Recycler adapter for this dropdown list
     */
    private class DDListAdapter extends RecyclerView.Adapter<DDListAdapter.TextViewHolder> {
        LayoutInflater mInflater;

        private DDListAdapter() {
            mInflater = LayoutInflater.from(getContext());
        }

        @NonNull
        @Override
        public TextViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TextViewHolder(mInflater.inflate(R.layout.adapter_custom_dd_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull final TextViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            final String itemTxt = l.get(position);
            holder.mTxt.setText(itemTxt);
            holder.itemView.setSelected(mDDTxt.getText().equals(itemTxt));
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDDTxt.getText().equals(itemTxt)) return;
                    mItemActivePos = position;
                    setTextAtPos(position).refresh();
                    if (callback != null) {
                        callback.onItemChanged(viewId, l.indexOf(itemTxt), itemTxt);
                    }
                    dismissDD();
                }
            });
        }

        @Override
        public int getItemCount() {
            return l != null ? l.size() : 0;
        }

        class TextViewHolder extends RecyclerView.ViewHolder {
            private TextView mTxt;

            private TextViewHolder(View itemView) {
                super(itemView);
                mTxt = itemView.findViewById(R.id.txt);
                DesignManager.INSTANCE.initParams(itemView, 0, mItemHeight, mStrokeSize, 0, mStrokeSize);
                DesignManager.INSTANCE.initParams(mTxt, 0, 0, mItemPadding / 2, 0, mItemPadding / 2);

                mTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, (mItemTextSize < 0) ? mDDTxt.getTextSize() : mItemTextSize);
                mTxt.setTextColor(DrawableUtil.INSTANCE.color((mItemTextColor != Integer.MIN_VALUE) ? mItemTextColor : ContextCompat.getColor(getContext(), android.R.color.holo_red_dark), (mItemTextHighlightColor != Integer.MIN_VALUE) ? mItemTextHighlightColor : Color.WHITE, (mItemTextSelColor != Integer.MIN_VALUE) ? mItemTextSelColor : Color.WHITE));
                itemView.setBackground(DrawableUtil.INSTANCE.drawable((mItemBgColor != Integer.MIN_VALUE) ? mItemBgColor
                                : ContextCompat.getColor(getContext(), android.R.color.holo_blue_bright),
                        (mItemHighlightColor != Integer.MIN_VALUE) ? mItemHighlightColor : Color.WHITE,
                        (mItemBgSelColor != Integer.MIN_VALUE) ? mItemBgSelColor : Color.WHITE));
            }
        }
    }
}