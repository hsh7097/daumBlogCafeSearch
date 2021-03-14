package com.homework.prehomework.utils.extension

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat



fun Context.getDrawableCompat(@DrawableRes drawableId: Int): Drawable? {
    return try {
        AppCompatResources.getDrawable(this, drawableId)
    } catch (e: Resources.NotFoundException) {
        null
    } catch (e: ExceptionInInitializerError) {
        null
    }
}

