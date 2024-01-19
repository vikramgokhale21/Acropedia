package com.example.acropedia.ui.model.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.acropedia.data.remote.SearchRepository
import com.example.acropedia.ui.model.SearchViewModel

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
    private val searchRepository: SearchRepository,
    val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(
                application = application,
                searchRepository = searchRepository
            )

            else -> error("Cannot create an instance of $modelClass")
        } as T
    }
}