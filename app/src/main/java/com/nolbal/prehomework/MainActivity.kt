package com.nolbal.prehomework

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.intentFor

class MainActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context?) {
            context?.startActivity(
                context.intentFor<MainActivity>()
            )
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}