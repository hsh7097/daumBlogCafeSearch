package com.thomas.daumBlogCafeTest.utils.bindingAdapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.thomas.daumBlogCafeTest.utils.extension.setImageUrl


@BindingAdapter("set_image")
fun setBindImage(imageView: ImageView, url: String?) {
    imageView.setImageUrl(url)
}