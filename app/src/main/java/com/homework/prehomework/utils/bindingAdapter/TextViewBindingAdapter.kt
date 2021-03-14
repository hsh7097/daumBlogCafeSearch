package com.homework.prehomework.utils.bindingAdapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.homework.prehomework.utils.extension.setTextHtml


@BindingAdapter("set_text_html")
fun setBindTextHtml(textView: TextView, text: String?) {
    textView.setTextHtml(text)
}