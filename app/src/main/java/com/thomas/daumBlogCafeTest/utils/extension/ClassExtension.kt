package com.thomas.daumBlogCafeTest.utils.extension

var DEFAULT_TAG = "DAUM_SEARCH_LOG"


fun logDebug(logData: String) {
    DEFAULT_TAG.logDebug(logData)
}

fun logError(logData: String) {
    DEFAULT_TAG.logError(logData)
}

