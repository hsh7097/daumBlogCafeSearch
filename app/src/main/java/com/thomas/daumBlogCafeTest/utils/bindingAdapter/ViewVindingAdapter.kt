package com.thomas.daumBlogCafeTest.utils.bindingAdapter

import android.view.View
import androidx.databinding.BindingAdapter
import com.thomas.daumBlogCafeTest.utils.extension.logError


@BindingAdapter("isVisible")
fun View.isVisible(
    isVisibility: Boolean = false
) {
    logError("isVisibility $isVisibility")
    this.visibility = if (isVisibility) View.VISIBLE else View.GONE
}
