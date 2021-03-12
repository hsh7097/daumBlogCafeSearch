package com.nolbal.prehomework.utils.extension

import android.util.Log
import com.nolbal.prehomework.BuildConfig


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
