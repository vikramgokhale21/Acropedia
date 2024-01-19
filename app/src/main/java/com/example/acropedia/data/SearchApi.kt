package com.example.acropedia.data

import com.example.acropedia.data.model.SearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("/software/acromine/dictionary.py")
    suspend fun getAcronymMeanings(@Query("sf") searchString: String): Response<List<SearchDto>>

}