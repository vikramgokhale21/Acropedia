package com.example.acropedia.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.acropedia.AcropediaApplication
import com.example.acropedia.data.mapper.SearchDtoToSearchResultsMapper
import com.example.acropedia.data.remote.SearchRepositoryImpl
import com.example.acropedia.ui.compose.SearchScreen
import com.example.acropedia.ui.model.SearchViewModel
import com.example.acropedia.ui.model.factory.SearchViewModelFactory

class SearchActivity : ComponentActivity() {

    private val factory: SearchViewModelFactory by lazy {
        SearchViewModelFactory(
            application = application,
            searchRepository = SearchRepositoryImpl(
                searchApi = AcropediaApplication.searchApi,
                searchResultsMapper = SearchDtoToSearchResultsMapper()
            )
        )
    }
    private val viewModel: SearchViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchScreen(
                state = viewModel.uiState.value,
                onSearchQueryChange = viewModel::onSearchQueryChange,
                onSearch = viewModel::onSearch
            )
        }
    }
}