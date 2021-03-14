package com.homework.prehomework.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homework.prehomework.R
import com.homework.prehomework.base.recyclerview.BasePagingRecyclerAdapter
import com.homework.prehomework.base.recyclerview.BaseViewHolder
import com.homework.prehomework.databinding.LayoutMainContentItemBinding
import com.homework.prehomework.databinding.LayoutMainHeaderItemBinding
import com.homework.prehomework.main.MainViewModel.SearchType
import com.homework.prehomework.network.model.responseModel.RpSearchResult
import com.homework.prehomework.utils.extension.*
import com.homework.prehomework.network.model.responseModel.RpSearchResult.Document as SearchModel

class MainContentAdapter : BasePagingRecyclerAdapter<SearchModel>() {
    init {
        setUseHeader()
    }

    enum class SortType(val sortName: String) {
        TITLE("타이틀 정렬"),
        DATE_TIME("시간 정렬")
    }

    private var selectSortType: SortType = SortType.TITLE


    interface OnMainContentListener {
        fun onChangeSearchType(searchType: SearchType)
        fun onCallSortDialog(sortType: SortType)
        fun onCallSearchDetail(searchModel: SearchModel)
    }

    private var onMainContentListener: OnMainContentListener? = null

    fun setOnMainContentListener(onMainContentListener: OnMainContentListener) {
        this.onMainContentListener = onMainContentListener
    }

    fun changeSortType(sortType: SortType) {
        selectSortType = sortType
        changeSortModel(model)
        notifyDataSetChanged()
    }

    private fun changeSortModel(modelArrayList: java.util.ArrayList<RpSearchResult.Document>?) {
        modelArrayList?.sortBy {
            when (selectSortType) {
                SortType.TITLE -> it.title
                SortType.DATE_TIME -> it.datetime
            }
        }
    }

    override fun addDataList(modelArrayList: java.util.ArrayList<RpSearchResult.Document>?) {
        changeSortModel(modelArrayList)
        super.addDataList(modelArrayList)
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
            mOnAddDataListener?.invoke()
            mPaginationLastPosition = position
        }
    }

    inner class HeaderViewHolder(
        private val parent: ViewGroup,
        private val binding: LayoutMainHeaderItemBinding =
            LayoutMainHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) : BaseViewHolder(binding.root) {

        init {
            binding.headerViewHolder = this
            setSpinner()
        }

        private fun setSpinner() {
            binding.filterSpinner.run {
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        logError("onItemSelected")
                        onMainContentListener?.onChangeSearchType(SearchType.values()[position])
                    }
                }

                adapter = ArrayAdapter<String>(
                    context,
                    R.layout.support_simple_spinner_dropdown_item,
                    SearchType.values().map { it.value }.toTypedArray()
                ).apply {
                    setDropDownViewResource(
                        R.layout.support_simple_spinner_dropdown_item
                    )
                }
            }
        }

        fun onClick(view: View) {
            when (view.id) {
                R.id.sortIv -> {
                    onMainContentListener?.onCallSortDialog(selectSortType)
                }
            }
        }
    }

    inner class ContentViewHolder(
        private val parent: ViewGroup,
        private val binding: LayoutMainContentItemBinding =
            LayoutMainContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) : BaseViewHolder(binding.root) {

        init {
            binding.contentViewHolder = this
        }

        fun bindData(searchModel: SearchModel) {
            data = searchModel
            with(binding) {
                nameTv.text = searchModel.getName()
                labelTv.text = searchModel.searchType?.value
                titleTv.setTextHtml(searchModel.title)
                dateTimeTv.text = searchModel.getShortDateTime()
                thumbIv.setImageUrlCenterCrop(
                    url = searchModel.thumbnail,
                    placeholder = R.drawable.placeholder
                )
            }
            updateBackground(searchModel.isOpened)
        }

        private fun updateBackground(isOpened: Boolean) {
            binding.contentLayout.setBackgroundDrawableResource(if (isOpened) R.color.gray_5 else R.color.white)
        }

        fun onAnimClick(view: View) {
            view.setClickAnimation {
                when (view.id) {
                    R.id.contentCv -> {
                        (data as? SearchModel)?.let { searchModel ->
                            searchModel.isOpened = true
                            onMainContentListener?.onCallSearchDetail(searchModel)
                            updateBackground(searchModel.isOpened)
                        }
                    }
                }
            }
        }
    }
}


@BindingAdapter("setMainContentAdapterData")
fun bindMainContentAdapterData(recyclerView: RecyclerView, items: ArrayList<SearchModel>?) {
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
