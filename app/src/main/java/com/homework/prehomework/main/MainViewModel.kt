package com.homework.prehomework.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.homework.prehomework.localRoom.RecentlyRoomDataBase
import com.homework.prehomework.localRoom.RecentlySearchWord
import com.homework.prehomework.localRoom.RecentlySearchWordDao
import com.homework.prehomework.main.adapter.MainContentAdapter.SortType
import com.homework.prehomework.main.repository.MainRepository
import com.homework.prehomework.main.repository.MainRepositoryImpl
import com.homework.prehomework.network.model.responseModel.RpSearchResult
import com.homework.prehomework.utils.SingleLiveData
import com.homework.prehomework.utils.extension.logError
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val mainRepository: MainRepository =
        MainRepositoryImpl(),
    private val recentlySearchDao: RecentlySearchWordDao =
        RecentlyRoomDataBase.getInstance().recentlyDao()
) : ViewModel() {

    companion object {
        private const val PAGING_DEFAULT = 1
    }

    enum class SearchType(val value: String) {
        ALL("All"),
        BLOG("Blog"),
        CAFE("Cafe")
    }

    //상품 리스트
    private val _contentListLiveData = MutableLiveData<ArrayList<RpSearchResult.Document>>()
    val contentListLiveData: LiveData<ArrayList<RpSearchResult.Document>> get() = _contentListLiveData

    //상품 페이징 리스트
    private val _contentPageListLiveData = MutableLiveData<ArrayList<RpSearchResult.Document>>()
    val contentPageListLiveData: LiveData<ArrayList<RpSearchResult.Document>> get() = _contentPageListLiveData

    //최근 검색어 리스트
    private val _recentlySearchWordListLiveData = MutableLiveData<ArrayList<RecentlySearchWord>>()
    val recentlySearchWordListLiveData: LiveData<ArrayList<RecentlySearchWord>> get() = _recentlySearchWordListLiveData

    //정렬 타입
    private val _showSortTypeDialogLiveData = SingleLiveData<SortType>()
    val showSortTypeDialogLiveData: LiveData<SortType> get() = _showSortTypeDialogLiveData

    //검색 상세 화면 실행
    private val _callSearchDetailActivityLiveData = SingleLiveData<RpSearchResult.Document>()
    val callSearchDetailActivityLiveData: LiveData<RpSearchResult.Document> get() = _callSearchDetailActivityLiveData

    //최근 검색어
    var searchWord: String? = null

    //검색 될 페이지
    var searchPaging: Int = PAGING_DEFAULT

    //검색 타입
    private var searchType: SearchType = SearchType.ALL

    /**
     * 검색 호출
     * @param searchWord 검색될 단어
     */
    fun callSearch(searchWord: String?) {
        if (searchWord.isNullOrEmpty()) return
        this.searchWord = searchWord
        recentlySearchDao.insert(RecentlySearchWord(word = searchWord))
        searchPaging = PAGING_DEFAULT
        when (searchType) {
            SearchType.BLOG -> getBlogSearch()
            SearchType.CAFE -> getCafeSearch()
            SearchType.ALL -> getAllSearch()
        }
    }


    /**
     * 검색 페이징 호출
     */
    fun callSearchPaging() {
        when (searchType) {
            SearchType.BLOG -> getBlogSearch()
            SearchType.CAFE -> getCafeSearch()
            SearchType.ALL -> getAllSearch()
        }
    }


    /**
     * 검색 타입 변경
     * @param searchType 변결될 타입
     * ALL, BLOG, CAFE
     */
    fun changeSearchType(searchType: SearchType) {
        if (this.searchType == searchType) return
        this.searchType = searchType
        callSearch(searchWord)
    }

    /**
     * 블로그 데이터 호출
     */
    private fun getBlogSearch() {
        if (searchWord.isNullOrEmpty()) return
        mainRepository.getBlogSearch(
            query = searchWord!!,
            page = searchPaging
        ).flatMap { responseSearch ->
            responseSearch.documents.forEach {
                it.searchType = RpSearchResult.SearchType.BLOG
            }
            return@flatMap Single.just(responseSearch.documents)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<RpSearchResult.Document>>() {
                override fun onSuccess(result: List<RpSearchResult.Document>) {
                    if (searchPaging == 1) {
                        _contentListLiveData.postValue(ArrayList(result))
                    } else {
                        _contentPageListLiveData.postValue(ArrayList(result))
                    }
                    searchPaging++
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }

    /**
     * 카페 데이터 호출
     */
    private fun getCafeSearch() {
        if (searchWord.isNullOrEmpty()) return
        mainRepository.getCafeSearch(
            query = searchWord!!,
            page = searchPaging
        ).flatMap { responseSearch ->
            responseSearch.documents.forEach {
                it.searchType = RpSearchResult.SearchType.CAFE
            }
            return@flatMap Single.just(responseSearch.documents)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<RpSearchResult.Document>>() {
                override fun onSuccess(result: List<RpSearchResult.Document>) {
                    if (searchPaging == 1) {
                        _contentListLiveData.postValue(ArrayList(result))
                    } else {
                        _contentPageListLiveData.postValue(ArrayList(result))
                    }
                    searchPaging++
                }

                override fun onError(e: Throwable) {
                    logError("onError $e")
                    e.printStackTrace()
                }
            })
    }

    /**
     * 블로그와 카페 데이터 호출
     */
    private fun getAllSearch() {
        if (searchWord.isNullOrEmpty()) return
        Single.zip(
            mainRepository.getBlogSearch(
                query = searchWord!!,
                page = searchPaging
            ),
            mainRepository.getCafeSearch(
                query = searchWord!!,
                page = searchPaging
            ),
            BiFunction { blogSearch: RpSearchResult, cafeSearch: RpSearchResult ->
                return@BiFunction ArrayList<RpSearchResult.Document>().apply {
                    blogSearch.documents.forEach {
                        it.searchType = RpSearchResult.SearchType.BLOG
                    }
                    cafeSearch.documents.forEach {
                        it.searchType = RpSearchResult.SearchType.CAFE
                    }
                    addAll(blogSearch.documents)
                    addAll(cafeSearch.documents)
                }
            }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<RpSearchResult.Document>>() {
                override fun onSuccess(result: List<RpSearchResult.Document>) {
                    if (searchPaging == 1) {
                        _contentListLiveData.postValue(ArrayList(result))
                    } else {
                        _contentPageListLiveData.postValue(ArrayList(result))
                    }
                    searchPaging++
                }

                override fun onError(e: Throwable) {
                    logError("onError $e")
                    e.printStackTrace()
                }
            })

    }

    /**
     * 최근 검색어 리스트 호출
     */
    fun callShowRecentSearchLayout() {
        val searchWordList = ArrayList(recentlySearchDao.getRecentSearchedWords())
        if (searchWordList.isNotEmpty()) {
            _recentlySearchWordListLiveData.postValue(searchWordList)
        }
    }

    /**
     * 정렬 다이얼로그 호출
     * @param sortType 변결될 타입
     * TITLE, DATE_TIME
     */
    fun callShowSortDialog(sortType: SortType) {
        _showSortTypeDialogLiveData.postValue(sortType)
    }

    /**
     * 상품 상세 실행
     * @param searchModel 검색된 상품
     */
    fun callSearchDetail(searchModel: RpSearchResult.Document) {
        _callSearchDetailActivityLiveData.postValue(searchModel)
    }

}