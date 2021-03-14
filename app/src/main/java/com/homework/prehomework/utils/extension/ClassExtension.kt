package com.homework.prehomework.utils.extension

var DEFAULT_TAG = "HOMEWORK_LOG"


fun logDebug(logData: String) {
    DEFAULT_TAG.logDebug(logData)
}

fun logError(logData: String) {
    DEFAULT_TAG.logError(logData)
}

