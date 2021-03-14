package com.homework.prehomework.utils.extension

import android.content.res.Resources
import java.text.NumberFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min



/**
 * Dp값을 PX로 변환
 */
fun Int.dpToPixel(): Int =
    (this * Resources.getSystem().displayMetrics.density).toInt()

