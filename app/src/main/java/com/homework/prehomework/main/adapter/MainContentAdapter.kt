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
import com.homework.prehomework.main.MainViewModel
import com.homework.prehomework.main.MainViewModel.SearchType
import com.homework.prehomework.network.model.responseModel.RpSearchResult
import com.homework.prehomework.network.model.responseModel.name
import com.homework.prehomework.utils.TimeUtils
import com.homework.prehomework.utils.extension.setImageUrlCenterCrop
import com.homework.prehomework.utils.extension.setTextHtml
import com.homework.prehomework.network.model.responseModel.RpSearchResult.Document as SearchModel

class MainContentAdapter(
    private val mainViewModel: MainViewModel
) : BasePagingRecyclerAdapter<SearchModel>() {
    init {
        setUseHeader()
    }

    enum class SortType(val sortName: String) {
        TITLE("타이틀 정렬"),
        DATE_TIME("시간 정렬")
    }

    private var selectSortType: SortType = SortType.TITLE


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
            mainViewModel.callSearchPaging()
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
                        mainViewModel.changeSearchType(SearchType.values()[position])
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
                    mainViewModel.callShowSortDialog(selectSortType)
                }
            }
        }
    }

    inner class ContentViewHolder(
        private val parent: ViewGroup,
        private val binding: LayoutMainContentItemBinding =
            LayoutMainContentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    ) : BaseViewHolder(binding.root) {

        fun bindData(searchModel: SearchModel) {
            with(binding) {
                nameTv.text = searchModel.name
                labelTv.text = searchModel.searchType?.value
                titleTv.setTextHtml(searchModel.title)
                dateTimeTv.text = TimeUtils.convertTimeYearMonthDay(searchModel.datetime)
                thumbIv.setImageUrlCenterCrop(searchModel.thumbnail)
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
