package com.homework.prehomework.utils.bindingAdapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.homework.prehomework.utils.extension.logError


@BindingAdapter("isVisible")
fun View.isVisible(
    isVisibility: Boolean = false) {
    logError("isVisibility $isVisibility")
    this.visibility = if (isVisibility) View.VISIBLE else View.GONE
}
