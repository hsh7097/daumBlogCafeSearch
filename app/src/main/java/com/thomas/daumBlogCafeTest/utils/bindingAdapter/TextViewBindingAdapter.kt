package com.thomas.daumBlogCafeTest.utils.bindingAdapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.thomas.daumBlogCafeTest.utils.extension.setTextHtml


@BindingAdapter("set_text_html")
fun setBindTextHtml(textView: TextView, text: String?) {
    textView.setTextHtml(text)
}