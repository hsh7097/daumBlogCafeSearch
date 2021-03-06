package com.homework.prehomework.utils.extension

import androidx.lifecycle.MutableLiveData

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
