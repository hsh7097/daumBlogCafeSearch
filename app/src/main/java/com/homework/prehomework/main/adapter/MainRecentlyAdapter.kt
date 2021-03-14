package com.homework.prehomework.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.homework.prehomework.base.recyclerview.BaseRecyclerAdapter
import com.homework.prehomework.base.recyclerview.BaseViewHolder
import com.homework.prehomework.databinding.LayoutRecentlySearchItemBinding
import com.homework.prehomework.localRoom.RecentlySearchWord

class MainRecentlyAdapter() : BaseRecyclerAdapter<RecentlySearchWord>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return RecentlyViewHolder(parent)
    }

    override fun onBindViewHolder(defaultViewHolder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(defaultViewHolder, position)
        (defaultViewHolder as? RecentlyViewHolder)?.run {
            getItem(position)?.let {
                bindData(it)
            }
        }
    }

    inner class RecentlyViewHolder(
        private val parent: ViewGroup,
        private val binding: LayoutRecentlySearchItemBinding =
            LayoutRecentlySearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
    ) : BaseViewHolder(binding.root) {

        fun bindData(searchModel: RecentlySearchWord) {
            with(binding) {
                recentlyTv.text = searchModel.word
            }
        }

    }
}


@BindingAdapter("setMainMainRecentlyAdapterData")
fun bindMainMainRecentlyAdapterData(
    recyclerView: RecyclerView,
    items: ArrayList<RecentlySearchWord>?
) {
    (recyclerView.adapter as MainRecentlyAdapter).run {
        clearAdapter()
        if (!items.isNullOrEmpty()) {
            addDataList(items)
        }
    }
}

