package com.homework.prehomework.utils.extension

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

var DEFAULT_TAG = "HOMEWORK_LOG"


fun logDebug(logData: String) {
    DEFAULT_TAG.logDebug(logData)
}

fun logError(logData: String) {
    DEFAULT_TAG.logError(logData)
}

