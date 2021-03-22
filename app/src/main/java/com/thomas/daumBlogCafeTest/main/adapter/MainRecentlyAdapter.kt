package com.thomas.daumBlogCafeTest.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.thomas.daumBlogCafeTest.base.recyclerview.BaseRecyclerAdapter
import com.thomas.daumBlogCafeTest.base.recyclerview.BaseViewHolder
import com.thomas.daumBlogCafeTest.databinding.LayoutRecentlySearchItemBinding
import com.thomas.daumBlogCafeTest.localRoom.RecentlySearchWord

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

