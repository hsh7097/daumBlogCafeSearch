package com.homework.prehomework.utils.extension

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


fun EditText?.getString(): String = this?.text?.toString()?.trimEnd(' ')?.trimStart(' ') ?: ""

/**
 * 포커스 해제
 */
fun EditText.focusOff() {
    hideKeyboard()
    this.run {
        isFocusableInTouchMode = false
        isFocusable = false
        Handler(Looper.getMainLooper()).postDelayed({
            isFocusableInTouchMode = true
            isFocusable = true
        }, 1000)
    }
}

/**
 * 텍스트 가져온 후 초기화
 */

fun EditText.getTextAndClear(): String {
    var returnValue: String
    this.run {
        returnValue = getString()
        text.clear()
        focusOff()
    }
    return returnValue
}

/**
 * 키보드 숨김
 */
fun EditText.hideKeyboard() {
    try {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (e: NullPointerException) {
        e.printStackTrace()
    }
}

/**
 * 엔터 클릭에 대해 콜백 호출
 */
fun EditText.registerEnterKeyListener(block: () -> Unit) {
    this.setOnKeyListener { _, keyCode, event ->
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            block.invoke()
            return@setOnKeyListener true
        }

        return@setOnKeyListener false
    }
}

