package com.homework.prehomework.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.homework.prehomework.base.recyclerview.BasePagingRecyclerAdapter
import com.homework.prehomework.base.recyclerview.BaseViewHolder
import com.homework.prehomework.databinding.LayoutMainContentItemBinding
import com.homework.prehomework.databinding.LayoutMainHeaderItemBinding
import com.homework.prehomework.main.MainViewModel
import com.homework.prehomework.main.MainViewModel.SearchType
import com.homework.prehomework.network.model.responseModel.RpSearchResult
import com.homework.prehomework.utils.TimeUtils
import com.homework.prehomework.utils.extension.logError
import com.homework.prehomework.utils.extension.setImageUrlCenterCrop
import com.homework.prehomework.utils.extension.setTextHtml
import com.homework.prehomework.network.model.responseModel.RpSearchResult.Document as SearchModel

class MainContentAdapter(
    private val mainViewModel: MainViewModel
) : BasePagingRecyclerAdapter<SearchModel>() {
    init {
        setUseHeader()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            POSTTYPE_HEADERVIEWHOLDER -> HeaderViewHolder(parent)
            else -> ContentViewHolder(parent)
        }
    }

    override fun onBindViewHolder(defaultViewHolder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(defaultViewHolder, position)

        when (defaultViewHolder) {
            is ContentViewHolder -> {
                getItem(position)?.let {
                    defaultViewHolder.bindData(it)
                }
            }
        }
    }


    override fun getNextData(position: Int) {
        if (position == mPaginationLastPosition) {
            return
        } else {
            mainViewModel.callSearchPaging()
            mPaginationLastPosition = position
        }
    }


    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        when (holder) {
            is HeaderViewHolder -> {
                holder.onAppear()
            }
        }
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        when (holder) {
            is HeaderViewHolder -> {
                holder.onDisappear()
            }
        }
    }

    inner class HeaderViewHolder(
        private val parent: ViewGroup,
        private val binding: LayoutMainHeaderItemBinding =
            LayoutMainHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) : BaseViewHolder(binding.root) {

        private val observer by lazy {
            Observer<SearchType> { searchType ->
                setSearchType(searchType)
            }
        }

        override fun onAppear() {
            mainViewModel.searchTypeLiveData.observeForever(observer)
        }

        override fun onDisappear() {
            mainViewModel.searchTypeLiveData.removeObserver(observer)
        }

        private fun setSearchType(searchType: SearchType) {
            binding.filterTv.text = searchType.name
        }
    }

    inner class ContentViewHolder(
        private val parent: ViewGroup,
        private val binding: LayoutMainContentItemBinding =
            LayoutMainContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) : BaseViewHolder(binding.root) {

        fun bindData(searchModel: SearchModel) {
            with(binding) {
                labelTv.text = searchModel.searchType?.value
                nameTv.text = when (searchModel.searchType) {
                    RpSearchResult.SearchType.BLOG -> searchModel.blogname
                    RpSearchResult.SearchType.CAFE -> searchModel.cafename
                    null -> ""
                }
                titleTv.setTextHtml(searchModel.title)
                dateTimeTv.text = TimeUtils.convertTimeYearMonthDay(searchModel.datetime)
                thumbIv.setImageUrlCenterCrop(searchModel.thumbnail)
            }

        }
    }
}


@BindingAdapter("setMainContentAdapterData")
fun bindMainContentAdapterData(recyclerView: RecyclerView, items: ArrayList<SearchModel>?) {
    logError("bindMainContentAdapterData $items")
    (recyclerView.adapter as MainContentAdapter).run {
        clearAdapter()
        if (!items.isNullOrEmpty()) {
            addDataList(items)
        }
    }
}

@BindingAdapter("setMainContentAdapterPagingData")
fun bindMainContentAdapterPagingData(recyclerView: RecyclerView, items: ArrayList<SearchModel>?) {
    (recyclerView.adapter as MainContentAdapter).run {
        if (!items.isNullOrEmpty()) {
            addDataList(items)
        }
    }
}
