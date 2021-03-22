package com.thomas.daumBlogCafeTest.detail

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.thomas.daumBlogCafeTest.R
import com.thomas.daumBlogCafeTest.base.BaseActivity
import com.thomas.daumBlogCafeTest.databinding.ActivitySearchDetailBinding
import com.thomas.daumBlogCafeTest.utils.extension.setClickAnimation
import com.thomas.daumBlogCafeTest.utils.extension.transitionLtoR
import com.thomas.daumBlogCafeTest.utils.extension.transitionRtoL
import com.thomas.daumBlogCafeTest.web.WebViewActivity
import org.jetbrains.anko.intentFor
import com.thomas.daumBlogCafeTest.network.model.responseModel.RpSearchResult.Document as SearchModel

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
        WebViewActivity.start(
            context = this,
            title = searchModel?.title,
            loadUrl = searchModel?.url
        )
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