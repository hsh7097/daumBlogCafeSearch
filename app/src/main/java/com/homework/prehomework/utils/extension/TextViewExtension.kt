/*
 * Created by Lee Oh Hyoung on 11/03/2020 .. 
 */
package com.homework.prehomework.utils.extension

import android.text.Html
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.widget.TextView
import com.homework.prehomework.utils.GlideImageGetter


fun TextView.setTextHtml(source: String?) {
    if (!source.isNullOrEmpty()) {
        text = Html.fromHtml(source, GlideImageGetter(context, this), null)
    }
}


