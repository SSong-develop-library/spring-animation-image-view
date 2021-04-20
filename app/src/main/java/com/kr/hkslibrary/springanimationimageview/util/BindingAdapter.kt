package com.hk.customcardview.util

import com.bumptech.glide.Glide
import com.hk.customcardview.implementspringanimation.SpringAnimationImageView

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("android:imageTest")
    fun imageTest(imageView: SpringAnimationImageView, url: String) {
        Glide.with(imageView.context)
            .asBitmap()
            .load(url)
            .into(imageView)
    }
}