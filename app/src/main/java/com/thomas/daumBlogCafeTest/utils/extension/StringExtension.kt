package com.thomas.daumBlogCafeTest.utils.extension

import android.util.Log
import com.thomas.daumBlogCafeTest.BuildConfig


fun String.logDebug(logData: String) {
    if (BuildConfig.DEBUG) {
        Log.d(this, logData)
    }
}

fun String.logError(logData: String) {
    if (BuildConfig.DEBUG) {
        Log.e(this, logData)
    }
}

fun String?.isNotNullEmpty(): Boolean {
    return isNullOrEmpty().not()
}
