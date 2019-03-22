package com.printlab.android.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.signature.ObjectKey

/**
 * [GlideImageView] is an [AppCompatImageView] which is used to load image from trackUrl and rootPath with the
 * help of [Glide]. We can set rounded corners and caching to this image with ease
 */
@Suppress("unused")
class GlideImageView : AppCompatImageView, RequestListener<Drawable> {

    /**
     * Flag to set whether caching is allwed or not
     */
    private var mCacheable: Boolean = true

    /**
     * Size of corner radius to this [GlideImageView].
     * By defaulf it is zero, that means no rounded corners are applied
     */
    private var mCornerRadius: Int = 0

    /**
     * Resize width of image
     */
    private var mResizeWidth: Int = 0

    /**
     * Resize height of image
     */
    private var mResizeHeight: Int = 0

    /**
     * View, it can be progress bar, which we will show when loading image,
     * and set its visibility gone, when view loaded
     */
    private var mLoaderView: View? = null
    /**
     * Key used when [mCacheable] is true, depends upon our need.
     * Sometimes you might refer data from the rootPath or trackUrl and the
     * image could've been changed, if we didn't set signature with unique key [mVersionKey],
     * [Glide] will give you the cached version always,
     * So to avoid that you could save unique key like file last_modified_time. And glide'll simply take care of everything.
     */
    private var mVersionKey: Any? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    /**
     * Method to set whether image is cacheable or not
     *
     * @param b do cache or not
     * @return [GlideImageView]
     */
    fun setCacheable(b: Boolean): GlideImageView {
        mCacheable = b
        return this
    }

    /**
     * Method to set corner-radius of this view
     *
     * @param radii corner radius for all sides equally
     * @return [GlideImageView]
     */
    fun setCornerRadius(radii: Int): GlideImageView {
        this.mCornerRadius = radii
        return this
    }

    /**
     * Method to resize image with given width and height
     * @param width width to be resized
     * @param height height to be resized
     */
    fun resize(width: Int, height: Int): GlideImageView {
        this.mResizeWidth = width
        this.mResizeHeight = height
        return this
    }

    /**
     * Method to set unique version key.
     * @param a unique key assigned to this [GlideImageView]
     */
    fun setVersionKey(a: Any): GlideImageView {
        this.mVersionKey = a
        return this
    }

    /**
     * Initializing progress view
     *
     * @param view loader view
     * @return [GlideImageView]
     */
    fun setLoaderView(view: View?): GlideImageView {
        mLoaderView = view
        return this
    }

    /**
     * Load image from path
     *
     * @param src - path of the image, it can be from trackUrl or filepath
     * @return [GlideImageView]
     */
    fun load(src: Any?): GlideImageView {
        mLoaderView?.visibility = View.VISIBLE
        val reqManager = Glide.with(context)
        reqManager.asBitmap()
        if (!mCacheable) {
            reqManager.applyDefaultRequestOptions(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .applyDefaultRequestOptions(RequestOptions.skipMemoryCacheOf(true))
        }
        val reqBuilder = reqManager.load(src)
        if ((mResizeWidth > 0 && mResizeHeight > 0) || mCornerRadius > 0 || mVersionKey != null) {
            val requestOptions = RequestOptions().dontAnimate()
//            requestOptions.dontAnimate().dontTransform()
            if (mCornerRadius > 0) requestOptions.transform(RoundedCorners(mCornerRadius))
            if (mResizeWidth > 0 && mResizeHeight > 0) requestOptions.override(mResizeWidth, mResizeHeight)
            if (mVersionKey != null) requestOptions.signature(ObjectKey(mVersionKey!!))
            reqBuilder.apply(requestOptions)
        }
        reqBuilder.listener(this)
        reqBuilder.into(this)
        return this
    }

    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
        return false
    }

    override fun onResourceReady(resource: Drawable?, model: Any?,
                                 target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
        mLoaderView?.visibility = View.GONE
        if (visibility == View.GONE || visibility == View.INVISIBLE)
            visibility = View.VISIBLE
        return false
    }
}