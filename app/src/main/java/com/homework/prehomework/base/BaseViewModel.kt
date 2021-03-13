package com.homework.prehomework.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.homework.prehomework.utils.SingleLiveData

abstract class BaseViewModel : ViewModel() {


    private val _finishCallLiveData =
        SingleLiveData<Unit>()
    val finishCallLiveData: LiveData<Unit> get() = _finishCallLiveData

    fun callFinish() {
        _finishCallLiveData.call()
    }


}