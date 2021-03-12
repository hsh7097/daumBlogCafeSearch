package com.nolbal.prehomework.utils.extension

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat


fun Context.showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    if (!message.isNullOrEmpty()) {
        Toast.makeText(applicationContext, message, duration).show()
    }
}

fun Context.showToast(stringId: Int, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(applicationContext, stringId, duration).show()

fun Context.getColorCompat(colorId: Int): Int =
    ContextCompat.getColor(this, colorId)

fun Context.getDrawableCompat(@DrawableRes drawableId: Int): Drawable? {
    return try {
        AppCompatResources.getDrawable(this, drawableId)
    } catch (e: Resources.NotFoundException) {
        null
    } catch (e: ExceptionInInitializerError) {
        null
    }
}

