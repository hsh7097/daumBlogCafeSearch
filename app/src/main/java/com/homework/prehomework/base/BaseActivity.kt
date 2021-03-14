package com.homework.prehomework.base

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.homework.prehomework.BuildConfig
import com.homework.prehomework.R
import com.homework.prehomework.utils.extension.logError
import org.jetbrains.anko.toast

open class BaseActivity : AppCompatActivity() {

    protected val TAG: String = this.javaClass.name


    /**
     * closeFlag를 원상태로 다시 돌리는 핸들러이다.
     */
    private var closeFlag = false

    override fun onResume() {
        super.onResume()
        if (BuildConfig.DEBUG) {
            TAG.logError("onResume activity >>>>>>>>>>>> [${javaClass.simpleName}] <<<<<<<<<<<<")
        }
    }


    //어플 종료
    fun finishApplication() {
        if (!closeFlag) {
            toast(R.string.toast_exit)
            //Flag상태값 변경
            closeFlag = true
            //2초후 Flag 원상태로 복귀.
            Handler(Looper.getMainLooper()).postDelayed({
                closeFlag = false
            }, 2000)
        } else {
            finishAffinity()
        }
    }


}