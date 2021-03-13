package com.homework.prehomework.main.repository

import com.homework.prehomework.network.model.responseModel.RpSearchResult
import io.reactivex.Single

interface MainRepository {


    /**
     * 블로그 검색
     */
    fun getBlogSearch(query: String, page: Int = 1): Single<RpSearchResult>

    /**
     * 카페 검색
     */
    fun getCafeSearch(query: String, page: Int = 1): Single<RpSearchResult>

}