package com.homework.prehomework.main.repository

import com.homework.prehomework.network.model.responseModel.RpSearchResult
import com.homework.prehomework.network.retrofit.NetworkManager
import io.reactivex.Single

class MainRepositoryImpl : MainRepository {

    override fun getBlogSearch(query: String, page: Int): Single<RpSearchResult> =
        NetworkManager.getInstance().getBlogSearch(query, page)

    override fun getCafeSearch(query: String, page: Int): Single<RpSearchResult> =
        NetworkManager.getInstance().getCafeSearch(query, page)


}