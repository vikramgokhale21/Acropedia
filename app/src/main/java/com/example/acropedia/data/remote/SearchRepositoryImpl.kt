package com.example.acropedia.data.remote

import com.example.acropedia.data.SearchApi
import com.example.acropedia.data.mapper.SearchDtoToSearchResultsMapper
import com.example.acropedia.data.model.SearchResultResponse

class SearchRepositoryImpl(
    private val searchApi: SearchApi,
    val searchResultsMapper: SearchDtoToSearchResultsMapper
) : SearchRepository {

    override suspend fun searchAcronym(searchString: String): SearchResultResponse? {
        return searchResultsMapper(searchApi.getAcronymMeanings(searchString))
    }

}