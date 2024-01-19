package com.example.acropedia.data.model

data class SearchResultResponse(
    val searchResult: SearchResult?,
    val errorMessage: String?,
    val errorCode: Int,
)

data class SearchResult(
    val shortForm: String,
    val longFormsList: List<LongFormList>
)

data class LongFormList(
    val longForm: String,
    val frequency: Int,
    val since: Int,
    val variations: List<LongForm>
)

data class LongForm(
    val longForm: String,
    val frequency: Int,
    val since: Int,
)