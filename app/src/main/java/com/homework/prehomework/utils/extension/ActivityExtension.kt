package com.homework.prehomework.utils.extension

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.homework.prehomework.R


fun Activity.transitionRtoL() {
    overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
}

fun Activity.transitionLtoR() {
    overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
}
