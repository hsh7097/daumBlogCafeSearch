package com.homework.prehomework.utils.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.homework.prehomework.utils.extension.setImageUrl


@BindingAdapter("set_image")
fun setBindImage(imageView: ImageView, url: String?) {
    imageView.setImageUrl(url)
}