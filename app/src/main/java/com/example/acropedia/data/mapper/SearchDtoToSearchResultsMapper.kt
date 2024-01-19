package com.example.acropedia.data.mapper

import com.example.acropedia.data.model.LongForm
import com.example.acropedia.data.model.LongFormList
import com.example.acropedia.data.model.SearchDto
import com.example.acropedia.data.model.SearchResult
import com.example.acropedia.data.model.SearchResultResponse
import retrofit2.Response

class SearchDtoToSearchResultsMapper :
    suspend (Response<List<SearchDto>>) -> SearchResultResponse? {
    override suspend fun invoke(searchResultResponse: Response<List<SearchDto>>): SearchResultResponse? {

        return if (!searchResultResponse.isSuccessful) {

            val code = searchResultResponse.code()
            val errorMessage = when (code) {
                401 -> "Unauthorized request. Please try again after sometime."
                404 -> "It seems the server isn't reachable. Please try again after sometime."
                500 -> "Something went wrong on our side. Please try again after sometime."
                504 -> "Gateway timed out. Please try again after sometime."
                else -> "We could not find the acronym you were looking for. Please enter a valid acronym and try again."
            }

            SearchResultResponse(
                searchResult = null,
                errorMessage = errorMessage,
                errorCode = code
            )

        } else {
            if (searchResultResponse.body()?.isNotEmpty() == true) {

                searchResultResponse.body()?.get(0)?.let { searchDto ->
                    val longFormList = mutableListOf<LongFormList>()
                    searchDto.lfs.forEach { longFormListDto ->

                        val variationsList = mutableListOf<LongForm>()

                        longFormListDto.vars.forEach { longFormDto ->
                            variationsList.add(
                                LongForm(
                                    longForm = longFormDto.lf,
                                    frequency = longFormDto.freq,
                                    since = longFormDto.since,
                                )
                            )
                        }

                        longFormList.add(
                            LongFormList(
                                longForm = longFormListDto.lf,
                                frequency = longFormListDto.freq,
                                since = longFormListDto.since,
                                variations = variationsList
                            )
                        )
                    }

                    SearchResultResponse(
                        searchResult = SearchResult(
                            shortForm = searchDto.sf,
                            longFormsList = longFormList.toList()
                        ),
                        errorMessage = null,
                        errorCode = searchResultResponse.code()
                    )

                }

            } else {

                SearchResultResponse(
                    searchResult = null,
                    errorMessage = "We could not find the acronym you were looking for. Please enter a valid acronym and try again.",
                    errorCode = searchResultResponse.code()
                )

            }
        }

    }
}