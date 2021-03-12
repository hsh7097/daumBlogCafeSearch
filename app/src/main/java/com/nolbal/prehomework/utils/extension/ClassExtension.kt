package com.nolbal.prehomework.utils.extension

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

var DEFAULT_TAG = "NOLBAL_LOG"


fun logDebug(logData: String) {
    DEFAULT_TAG.logDebug(logData)
}

fun logError(logData: String) {
    DEFAULT_TAG.logError(logData)
}


fun Double.toRadian(): Double = ((this * Math.PI) / 180)

fun Int.toDeviceAngle(): Double = 360.toDouble() / this

fun Double.toRounds(): Double = Math.round(this * 1e10) * 1e-10


fun Boolean.isVisible(): Int = if (this) View.VISIBLE else View.GONE


fun getCurrentTime(type: String): String = SimpleDateFormat(type).format(Date(System.currentTimeMillis()))
