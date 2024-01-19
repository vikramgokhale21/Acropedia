package com.example.acropedia.ui.model

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acropedia.R
import com.example.acropedia.data.model.SearchResultResponse
import com.example.acropedia.data.remote.SearchRepository
import com.example.acropedia.ui.model.state.SearchUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SearchViewModel(
    private val application: Application,
    private val searchRepository: SearchRepository,
) : ViewModel() {

    var uiState = mutableStateOf(
        SearchUiState.Success(
            searchQuery = "",
            history = emptySet(),
            isSubmitting = false,
            searchResultResponse = null,
        )
    )

    fun onSearchQueryChange(searchQuery: String) {
        uiState.value = uiState.value.copy(
            searchQuery = searchQuery,
            searchResultResponse = null,
        )
    }

    fun onSearch(searchQuery: String) {

        uiState.value = uiState.value.copy(
            searchQuery = searchQuery,
            history = if (searchQuery.isNotEmpty()) uiState.value.history + searchQuery else uiState.value.history,
            searchResultResponse = null,
            isSubmitting = true,
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                uiState.value = uiState.value.copy(
                    searchResultResponse = searchRepository.searchAcronym(uiState.value.searchQuery),
                    isSubmitting = false,
                )
            } catch (e: Exception) {
                uiState.value = uiState.value.copy(
                    searchResultResponse = SearchResultResponse(
                        searchResult = null,
                        errorMessage = application.getString(R.string.generic_error_message),
                        errorCode = 503 //Service Unavailable
                    ),
                    isSubmitting = false
                )
            }

        }

    }

}