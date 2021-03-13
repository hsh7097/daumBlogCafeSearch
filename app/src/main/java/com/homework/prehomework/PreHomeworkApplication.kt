package com.homework.prehomework

import android.app.Application
import android.content.Context

class PreHomeworkApplication : Application() {

    companion object {
        private lateinit var instance: PreHomeworkApplication
        private lateinit var preHomeApplicationContext: Context

        private fun setInstance(application: PreHomeworkApplication) {
            instance = application
            preHomeApplicationContext = instance
        }
        fun getInstance() = instance
        fun getPreHomeApplicationContext() = preHomeApplicationContext


    }

    override fun onCreate() {
        super.onCreate()
        setInitialize()
    }

    private fun setInitialize() {
        setInstance(this)
    }

}