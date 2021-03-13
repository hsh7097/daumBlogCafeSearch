/*
 * Created by Lee Oh Hyoung on 11/03/2020 .. 
 */
package com.homework.prehomework.utils.extension

import android.graphics.drawable.Drawable
import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView
import com.homework.prehomework.utils.GlideImageGetter

fun TextView.setTextColorResource(resource: Int) =
    this.setTextColor(context.getColorCompat(resource))


fun TextView.setTextHtml(source: String?) {
    if (!source.isNullOrEmpty()) {
        text = Html.fromHtml(source, GlideImageGetter(context, this), null)
    }
}

/**
 * 밑줄 추가
 *
 * @param textView 밑줄이 추가될 텍스트뷰
 */
fun TextView.setUnderLine() {
    SpannableString(text.toString()).apply {
        setSpan(UnderlineSpan(), 0, text.length, 0)
    }.let {
        text = it
    }
}


