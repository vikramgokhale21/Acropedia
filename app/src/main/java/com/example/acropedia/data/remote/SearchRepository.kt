package com.example.acropedia.data.remote

import com.example.acropedia.data.model.SearchResultResponse

interface SearchRepository {
    suspend fun searchAcronym(searchString: String): SearchResultResponse?
}