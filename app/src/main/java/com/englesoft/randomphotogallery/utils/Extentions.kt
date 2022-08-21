package com.englesoft.randomphotogallery.utils

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.englesoft.randomphotogallery.R

fun ImageView.loadImageFromUrl(
    context: Context,
    aImageUrl: String,
    aPlaceHolderImage: Int = R.drawable.ic_launcher_foreground,
    aErrorImage: Int = R.drawable.ic_launcher_foreground
) {
    try {
        if (aImageUrl.isNotEmpty()) {
            Glide.with(context).load(aImageUrl).placeholder(aPlaceHolderImage)
                .diskCacheStrategy(
                    DiskCacheStrategy.AUTOMATIC
                ).error(aErrorImage).into(this)
        } else {
            loadImageFromDrawable(aPlaceHolderImage, context)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun ImageView.loadImageFromDrawable(@DrawableRes aPlaceHolderImage: Int, context: Context) {
    Glide.with(context).load(aPlaceHolderImage).diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(this)
}