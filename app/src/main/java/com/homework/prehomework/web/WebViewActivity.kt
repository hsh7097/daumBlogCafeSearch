package com.homework.prehomework.web

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.homework.prehomework.R
import com.homework.prehomework.base.BaseActivity
import com.homework.prehomework.databinding.ActivityWebBinding
import com.homework.prehomework.utils.extension.setClickAnimation
import com.homework.prehomework.utils.extension.transitionLtoR
import com.homework.prehomework.utils.extension.transitionRtoL
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.toast

class WebViewActivity : BaseActivity() {
    companion object {
        private const val TITLE = "title"
        private const val LOAD_URL = "loadUrl"


        fun start(context: Context?, title: String?, loadUrl: String?) {
            context?.startActivity(
                context.intentFor<WebViewActivity>(
                    TITLE to title,
                    LOAD_URL to loadUrl
                ).singleTop()
            )
            if (context is Activity) {
                context.transitionRtoL()
            }

        }

    }

    private lateinit var binding: ActivityWebBinding

    private val title by lazy {
        intent.getStringExtra(TITLE)
    }

    private val loadUrl by lazy {
        intent.getStringExtra(LOAD_URL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setAppBarTitle()
        setWebView()
        loadWebView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        transitionLtoR()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web)
        binding.lifecycleOwner = this
        binding.webViewActivity = this
    }

    private fun setAppBarTitle() {
        binding.title = title
    }

    private fun setWebView() {
        with(binding.webView) {
            settings.run {
                javaScriptCanOpenWindowsAutomatically = true
                javaScriptEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
                isVerticalScrollBarEnabled = true
            }
            webViewClient = WebClient()
        }
    }

    private fun loadWebView() {
        if (loadUrl.isNullOrEmpty()) {
            toast("올바르지 않은 호출 입니다!")
            finish()
        } else {
            binding.webView.loadUrl(loadUrl!!)
        }
    }

    fun onAnimClick(view: View) {
        view.setClickAnimation {
            when (view.id) {
                R.id.backIv -> {
                    onBackPressed()
                }
            }
        }
    }


    internal class WebClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}
