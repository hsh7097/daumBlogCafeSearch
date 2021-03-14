package com.homework.prehomework.network.model.responseModel

import com.homework.prehomework.network.model.responseModel.RpSearchResult.SearchType

data class RpSearchResult(
    val documents: List<Document> = listOf(),
    val meta: Meta? = Meta()
) {

    enum class SearchType(val value: String) {
        BLOG("B"),
        CAFE("C")
    }

    data class Document(
        val blogname: String? = "",
        val cafename: String? = "",
        val contents: String? = "",
        val datetime: String? = "",
        val thumbnail: String? = "",
        val title: String? = "",
        val url: String? = "",
        var searchType: SearchType? = null
    )

    data class Meta(
        val is_end: Boolean? = false,
        val pageable_count: Int? = 0,
        val total_count: Int? = 0
    )


}

val RpSearchResult.Document.name: String?
    get() = when (searchType) {
        SearchType.BLOG -> blogname
        SearchType.CAFE -> cafename
        null -> ""
    }
