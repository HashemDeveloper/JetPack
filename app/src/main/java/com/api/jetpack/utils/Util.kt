package com.api.jetpack.utils

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.api.jetpack.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun AppCompatImageView.loadImage(imageUrl: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_error_red_24dp)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(imageUrl).into(this)
}

@BindingAdapter("app:imageUrl")
fun loadImage(view: AppCompatImageView, url: String?) {
    view.loadImage(url, getProgressDrawable(view.context))
}
@BindingAdapter("app:src")
fun loadImageSrc(view: AppCompatImageView, url: String?) {
    view.loadImage(url, getProgressDrawable(view.context))
}