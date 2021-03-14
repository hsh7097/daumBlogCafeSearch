package com.homework.prehomework.detail

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.homework.prehomework.R
import com.homework.prehomework.base.BaseActivity
import com.homework.prehomework.databinding.ActivitySearchDetailBinding
import com.homework.prehomework.utils.extension.setClickAnimation
import com.homework.prehomework.utils.extension.transitionLtoR
import com.homework.prehomework.utils.extension.transitionRtoL
import com.homework.prehomework.web.WebViewActivity
import org.jetbrains.anko.intentFor
import com.homework.prehomework.network.model.responseModel.RpSearchResult.Document as SearchModel

class SearchDetailActivity : BaseActivity() {

    companion object {
        private const val SEARCH_MODEL = "SEARCH_MODEL"

        fun start(context: Context?, searchModel: SearchModel) {
            context?.startActivity(
                context.intentFor<SearchDetailActivity>(
                    SEARCH_MODEL to searchModel
                )
            )
            if (context is Activity) {
                context.transitionRtoL()
            }
        }
    }

    private lateinit var binding: ActivitySearchDetailBinding

    private val searchModel by lazy {
        intent.getSerializableExtra(SEARCH_MODEL) as? SearchModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setSearchModel()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        transitionLtoR()
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_detail)
        binding.lifecycleOwner = this
        binding.searchDetailActivity = this
    }

    private fun setSearchModel() {
        binding.searchModel = searchModel
    }

    private fun startWebActivity() {
//        WebViewActivity.start(
//            context = this,
//            title = searchModel?.title,
//            loadUrl = searchModel?.url
//        )
    }

    fun onAnimClick(view: View) {
        view.setClickAnimation {
            when (view.id) {
                R.id.backIv -> {
                    onBackPressed()
                }
                R.id.linkTv -> {
                    startWebActivity()
                }
            }
        }
    }

}