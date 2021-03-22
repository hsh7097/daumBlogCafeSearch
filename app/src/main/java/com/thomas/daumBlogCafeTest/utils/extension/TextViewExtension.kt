/*
 * Created by Lee Oh Hyoung on 11/03/2020 .. 
 */
package com.thomas.daumBlogCafeTest.utils.extension

import android.text.Html
import android.widget.TextView
import com.thomas.daumBlogCafeTest.utils.GlideImageGetter


fun TextView.setTextHtml(source: String?) {
    if (!source.isNullOrEmpty()) {
        text = Html.fromHtml(source, GlideImageGetter(context, this), null)
    }
}


