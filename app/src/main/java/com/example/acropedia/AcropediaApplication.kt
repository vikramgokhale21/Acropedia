package com.example.acropedia

import android.app.Application
import com.example.acropedia.data.SearchApi
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AcropediaApplication : Application() {
    companion object {
        lateinit var searchApi: SearchApi
    }

    override fun onCreate() {
        super.onCreate()
        searchApi = Retrofit.Builder()
            .baseUrl("http://www.nactem.ac.uk")
            .addConverterFactory(GsonConverterFactory.create((GsonBuilder().create())))
            .build()
            .create(SearchApi::class.java)
    }
}