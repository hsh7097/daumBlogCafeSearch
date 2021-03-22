package com.thomas.daumBlogCafeTest

import android.app.Application
import android.content.Context

class DaumBlogCafeApplication : Application() {

    companion object {
        private lateinit var instance: DaumBlogCafeApplication
        private lateinit var preHomeApplicationContext: Context

        private fun setInstance(application: DaumBlogCafeApplication) {
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