package com.thomas.daumBlogCafeTest.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.thomas.daumBlogCafeTest.R
import com.thomas.daumBlogCafeTest.base.BaseActivity
import com.thomas.daumBlogCafeTest.base.recyclerview.BaseViewHolder
import com.thomas.daumBlogCafeTest.databinding.ActivityMainBinding
import com.thomas.daumBlogCafeTest.detail.SearchDetailActivity
import com.thomas.daumBlogCafeTest.localRoom.RecentlySearchWord
import com.thomas.daumBlogCafeTest.main.adapter.MainContentAdapter
import com.thomas.daumBlogCafeTest.main.adapter.MainContentAdapter.SortType
import com.thomas.daumBlogCafeTest.main.adapter.MainRecentlyAdapter
import com.thomas.daumBlogCafeTest.network.model.responseModel.RpSearchResult
import com.thomas.daumBlogCafeTest.utils.extension.*
import org.jetbrains.anko.intentFor

class MainActivity : BaseActivity() {

    companion object {
        fun start(context: Context?) {
            context?.startActivity(
                context.intentFor<MainActivity>()
            )
        }
    }

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private val mainContentAdapter by lazy {
        MainContentAdapter()
    }

    private val mainRecentlyAdapter by lazy {
        MainRecentlyAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        setRecyclerView()
        setListener()
        observerLiveData()
    }

    override fun onBackPressed() {
        if (binding.recentSearchBackLayout.visibility == View.VISIBLE) {
            binding.searchEt.focusOff()
        } else {
            finishApplication()
        }
    }

    private fun setBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.run {
            lifecycleOwner = this@MainActivity
            mainActivity = this@MainActivity
            viewModel = mainViewModel
        }
    }

    //RecyclerView 설정
    private fun setRecyclerView() {
        with(binding) {
            contentRv.run {
                layoutManager = LinearLayoutManager(this@MainActivity)
                itemAnimator = DefaultItemAnimator()
                addItemDecoration(
                    ItemVerticalDecorator(
                        topMargin = 8.dpToPixel(),
                        bottomMargin = 8.dpToPixel(),
                        startMargin = 0.dpToPixel(),
                        endMargin = 0.dpToPixel(),
                        firstTopMargin = 0.dpToPixel(),
                        lastBottomMargin = 8.dpToPixel()
                    )
                )
                adapter = mainContentAdapter
            }

            recentlyRv.run {
                layoutManager = LinearLayoutManager(this@MainActivity)
                itemAnimator = DefaultItemAnimator()
                adapter = mainRecentlyAdapter
            }
        }

    }

    private fun observerLiveData() {
        with(mainViewModel) {
            //상품 정보 호출로 검색입력 포커스 아웃
            contentListLiveData.observe(this@MainActivity, Observer {
                finishGetContentList()
            })

            //최근 검색어 보여지도록 호출
            recentlySearchWordListLiveData.observe(this@MainActivity, Observer {
                showRecentSearchLayout(true)
            })

            //정렬 타입을 선택하는 다이얼로그 생성
            showSortTypeDialogLiveData.observe(this@MainActivity, Observer {
                showSortDialog(it)
            })

            //상품 상세 화면 이동
            callStartSearchDetailActivityLiveData.observe(this@MainActivity, Observer {
                startSearchDetailActivity(it)
            })

            //로딩 다이얼로그 생성
            callShowLoadingLiveData.observe(this@MainActivity, Observer {
                showLoadingPopup()
            })
        }
    }


    private fun callSearch() {
        val searchText: String = binding.searchEt.getTextAndFocusOff()
        if (searchText.isNotNullEmpty()) {
            mainViewModel.callSearch(searchText)
        }
    }

    private fun clearEditText() {
        binding.searchEt.clearText()
    }

    private fun finishGetContentList() {
        dismissLoadingPopup()
        binding.searchEt.focusOff()
    }

    private fun setListener() {
        binding.searchEt.run {
            registerEnterKeyListener {
                callSearch()
            }
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    mainViewModel.callShowRecentSearchLayout()
                } else {
                    showRecentSearchLayout(false)
                }
            }
        }

        mainRecentlyAdapter.setOnItemClickListener(object : BaseViewHolder.OnItemClickListener {
            override fun onItemClick(position: Int, model: Any?) {
                (model as? RecentlySearchWord)?.let {
                    binding.searchEt.setText(it.word)
                    callSearch()
                }
            }
        })
        mainContentAdapter.run {
            setOnAddDataListener {
                mainViewModel.callSearchPaging()
            }
            setOnMainContentListener(object : MainContentAdapter.OnMainContentListener {
                override fun onChangeSearchType(searchType: MainViewModel.SearchType) {
                    mainViewModel.changeSearchType(searchType)
                }

                override fun onCallSortDialog(sortType: SortType) {
                    mainViewModel.callShowSortDialog(sortType)
                }

                override fun onCallSearchDetail(searchModel: RpSearchResult.Document) {
                    mainViewModel.callStartSearchDetail(searchModel)
                }

            })
        }
    }

    private fun showRecentSearchLayout(isVisible: Boolean) {
        binding.recentSearchBackLayout.isVisible = isVisible
    }

    private fun showSortDialog(sortType: SortType) {
        val defaultSelectedIndex = SortType.values().indexOf(sortType)
        var selectedIndex = defaultSelectedIndex

        //기획서 상 버튼 위치가 달라 positive <> negative 액션 변경
        AlertDialog
            .Builder(this)
            .setTitle("정렬")
            .setSingleChoiceItems(
                SortType.values().map { it.sortName as CharSequence }.toTypedArray(),
                selectedIndex
            ) { _, index ->
                selectedIndex = index
            }.setNegativeButton(
                "선택"
            ) { _, _ ->
                mainContentAdapter.changeSortType(SortType.values()[selectedIndex])
            }
            .setPositiveButton("취소", null)
            .show()

    }

    private fun startSearchDetailActivity(searchModel: RpSearchResult.Document) {
        SearchDetailActivity.start(
            context = this,
            searchModel = searchModel
        )
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.recentSearchBackLayout -> {
                binding.searchEt.focusOff()
                showRecentSearchLayout(false)
            }
        }
    }

    fun onAnimClick(view: View) {
        view.setClickAnimation {
            when (view.id) {
                R.id.searchIv -> {
                    callSearch()
                }
                R.id.deleteIv -> {
                    clearEditText()
                }
            }
        }
    }

}