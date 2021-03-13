package com.homework.prehomework.utils.extension

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


fun EditText?.getString(): String = this?.text?.toString()?.trimEnd(' ')?.trimStart(' ') ?: ""

infix fun EditText.equalsText(checkString: String): Boolean = this.getString() == checkString
infix fun EditText.equalsText(checkEditText: EditText): Boolean =
    this.getString() == checkEditText.getString()

fun EditText.clearText() {
    this.run {
        text.clear()
        focusOn()
    }
}

fun EditText.focusOn() {
    this.run {
        isFocusableInTouchMode = true
        requestFocus()
        showKeyBoard()
    }
}

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

fun EditText.getTextAndClear(): String {
    var returnValue: String
    this.run {
        returnValue = text.toString()
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


fun EditText.toUsable(usable: Boolean) {
    this.run {
        isClickable = usable
        isEnabled = usable
        isFocusable = usable
        isFocusableInTouchMode = usable

    }
}

fun EditText.showKeyBoard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)

}

fun EditText.registerEnterKeyListener(block: () -> Unit) {
    setOnKeyListener { _, keyCode, event ->
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            block.invoke()
            return@setOnKeyListener true
        }

        return@setOnKeyListener false
    }
}

