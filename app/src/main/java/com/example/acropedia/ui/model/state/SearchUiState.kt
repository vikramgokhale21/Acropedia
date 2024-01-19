package com.example.acropedia.ui.model.state

import com.example.acropedia.data.model.SearchResultResponse

interface SearchUiState {

    data class Success(
        val searchQuery: String,
        val history: Set<String>,
        val isSubmitting: Boolean,
        val searchResultResponse: SearchResultResponse?,
    ) : SearchUiState

}