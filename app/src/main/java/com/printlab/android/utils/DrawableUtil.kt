package com.printlab.android.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.StateListDrawable
import android.graphics.drawable.shapes.RectShape
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.StateSet

/**
 * [DrawableUtil] is used to create [GradientDrawable], [ShapeDrawable],
 * [ColorStateList] and [StateListDrawable] programmatically
 *
 */
object DrawableUtil {

    /**
     * Creates [StateListDrawable], used to set background drawable for
     * different states such as normal, pressed and selected
     *
     * @param defColor     normal/default state color resource id
     * @param pressedColor pressed state color resource id
     * @param selColor     selected state color resource id
     * @return [StateListDrawable] instance
     */
    fun drawable(@ColorInt defColor: Int, @ColorInt pressedColor: Int, @ColorInt selColor: Int): StateListDrawable {
        val out = StateListDrawable()
        out.addState(intArrayOf(android.R.attr.state_pressed), draw(pressedColor))
        out.addState(intArrayOf(android.R.attr.state_selected), draw(selColor))
        out.addState(StateSet.WILD_CARD, draw(defColor))
        return out
    }

    /**
     * Creates [StateListDrawable], used to set background drawable for
     * different states such as normal, pressed and selected
     *
     * @param defColor     normal/default state drawable resource id
     * @param pressedColor pressed state drawable resource id
     * @param selColor     selected state drawable resource id
     * @return [StateListDrawable] instance
     */
    fun drawable(defColor: Drawable?, pressedColor: Drawable?, selColor: Drawable?): StateListDrawable {
        val out = StateListDrawable()
        if (pressedColor != null)
            out.addState(intArrayOf(android.R.attr.state_pressed), pressedColor)
        if (selColor != null) out.addState(intArrayOf(android.R.attr.state_selected), selColor)
        if (defColor != null) out.addState(StateSet.WILD_CARD, defColor)
        return out
    }

    /**
     * Creates drawable with specific height, used for divider in `ListView`/`{ RecyclerView}`
     *
     * @param context        context reference
     * @param mDividerHeight height of the drawable
     * @param mDividerColor  color of the drawable
     * @return [ShapeDrawable] instance
     */
    fun drawable(mDividerHeight: Int, @ColorInt mDividerColor: Int): ShapeDrawable {
        val badge = ShapeDrawable(RectShape())
        badge.intrinsicHeight = mDividerHeight
        badge.paint.color = mDividerColor
        return badge
    }

    /**
     * Creates [ColorStateList], used to set text colors for
     * different states such as normal, pressed and selected
     *
     * @param defColor     normal/default state color resource id
     * @param pressedColor pressed state color resource id
     * @param selColor     selected state color resource id
     * @return [StateListDrawable] instance
     */
    fun color(@ColorInt defColor: Int, @ColorInt pressedColor: Int, @ColorInt selColor: Int): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_pressed), // pressed state
            intArrayOf(android.R.attr.state_selected), // selected state
            intArrayOf()
        )// normal
        val colors = intArrayOf(pressedColor, selColor, defColor)
        return ColorStateList(states, colors)
    }

    /**
     * Creates drawable by using [GradientDrawable], we can use this to create
     * cornered drawable by passing `50%` size of the view to radius
     *
     * @param color  color of the cornered
     * @param radius border radius of this drawable
     * @return [GradientDrawable] instance
     */
    fun cornered(@ColorInt color: Int, radius: Int): GradientDrawable {
        return draw(color, 0, -1, radius.toFloat())
    }

    /**
     * Creates drawable by using [GradientDrawable], It sets background color
     * and corner-radius in `float[] radius`
     *
     * @param colorId background color resource id
     * @param corners corner radius sizes
     * @return [GradientDrawable] instance
     */
    fun draw(@ColorInt colorId: Int, corners: FloatArray): GradientDrawable {
        return draw(colorId, 0, 0, corners)
    }

    /**
     * Creates drawable with specific height, used for divider in `ListView`/`{ RecyclerView}`
     *
     * @param mDividerHeight height of the drawable
     * @param mDividerColor  color of the drawable
     * @return [ShapeDrawable] instance
     */
    fun draw(mDividerHeight: Int, @ColorInt mDividerColor: Int): ShapeDrawable {
        val badge = ShapeDrawable(RectShape())
        badge.intrinsicHeight = mDividerHeight
        badge.paint.color = mDividerColor
        return badge
    }

    /**
     * Creates drawable by using [GradientDrawable]
     *
     * @param colorId       background color resource id
     * @param strokeWidth   stroke size of the drawable
     * @param strokeColorId stroke color resource id
     * @param corners       corner radius sizes
     * @return [GradientDrawable] instance
     */
    fun draw(
        @ColorInt colorId: Int, strokeWidth: Int, @ColorInt strokeColorId: Int,
        corners: FloatArray
    ): GradientDrawable {

        val shapeD = GradientDrawable()
        shapeD.shape = GradientDrawable.RECTANGLE
        if (colorId != 0) shapeD.setColor(colorId)
        if (strokeColorId != 0) shapeD.setStroke(strokeWidth, strokeColorId)
        shapeD.cornerRadii = corners
        return shapeD
    }

    /**
     * Creates drawable by using [GradientDrawable]
     *
     * @param colorId       background color resource id
     * @param strokeWidth   stroke size of the drawable
     * @param strokeColorId stroke color resource id
     * @param corners       corner radius size
     * @return [GradientDrawable] instance
     */
    @JvmOverloads
    fun draw(
        @ColorInt colorId: Int, strokeWidth: Int = 0, @ColorInt strokeColorId: Int = -1,
        corners: Float = 0f
    ): GradientDrawable {
        val shapeD = GradientDrawable()
        shapeD.shape = GradientDrawable.RECTANGLE
        if (colorId != 0) shapeD.setColor(colorId)
        // shapeD.setColor(ContextCompat.getColor(context, colorId));
        if (strokeColorId != 0) shapeD.setStroke(strokeWidth, strokeColorId)
        shapeD.cornerRadius = corners
        return shapeD
    }
}