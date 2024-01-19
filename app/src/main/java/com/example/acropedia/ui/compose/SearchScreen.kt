package com.example.acropedia.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.acropedia.R
import com.example.acropedia.ui.model.state.SearchUiState
import com.example.acropedia.ui.theme.AcropediaTheme

val openSansFamily = FontFamily(
    Font(R.font.opensans_bold, FontWeight.Bold),
    Font(R.font.opensans_regular, FontWeight.Normal),
    Font(R.font.opensans_light, FontWeight.Light)
)

val headingTextStyle =
    TextStyle(
        color = Color.Black,
        fontSize = 20.sp,
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Bold
    )
val heading2TextStyle =
    TextStyle(
        color = Color.Black,
        fontSize = 16.sp,
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Bold
    )

val heading3TextStyle =
    TextStyle(
        color = Color.Black,
        fontSize = 16.sp,
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Normal
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchUiState.Success,
    onSearchQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {

    var active by remember { mutableStateOf(false) }

    AcropediaTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                Spacer(modifier = Modifier.size(8.dp))
                SearchBar(
                    query = state.searchQuery,
                    onQueryChange = onSearchQueryChange,
                    onSearch = {
                        active = false
                        onSearch(state.searchQuery)
                    },
                    active = active,
                    onActiveChange = {
                        active = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.search_label),
                            style = heading3TextStyle
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search_icon_content_description),
                        )
                    },
                    trailingIcon = {
                        if (active) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(id = R.string.close_icon_content_description),
                                modifier = Modifier.clickable(onClick = {
                                    if (state.searchQuery.isNotEmpty()) {
                                        onSearchQueryChange("")
                                    } else {
                                        active = false
                                    }
                                })
                            )
                        }
                    }
                ) {
                    if (active) {
                        state.history.forEach {
                            Row(
                                modifier = Modifier
                                    .padding(horizontal = 24.dp, vertical = 8.dp)
                                    .fillMaxWidth()
                                    .clickable(onClick = {
                                        active = false
                                        onSearch(it)
                                    })
                            ) {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = stringResource(id = R.string.history_icon_content_description),
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                )
                                Text(
                                    text = it,
                                    style = heading3TextStyle,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                if (state.isSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(64.dp)
                            .size(size = 128.dp)
                            .align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

                state.searchResultResponse?.errorMessage?.let {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.oops_label),
                            style = headingTextStyle,
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = stringResource(id = R.string.oops_icon_content_description),
                            tint = Color.Red,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .size(80.dp)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = it,
                            style = heading3TextStyle,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }

                state.searchResultResponse?.searchResult?.let {
                    LazyColumn(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {

                        item {
                            Text(
                                text = state.searchQuery,
                                style = headingTextStyle,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }

                        item {
                            ListHeader(headingString = stringResource(id = R.string.meaning_label))
                        }

                        items(items = it.longFormsList) { longFormListElement ->
                            Column {
                                ListElement(elementString = longFormListElement.longForm)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListHeader(headingString: String) {
    Row {
        Box(
            modifier = Modifier
                .size(size = 8.dp)
                .clip(RectangleShape)
                .align(Alignment.CenterVertically)
                .background(color = Color.Black)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = headingString,
            style = heading2TextStyle,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun ListElement(elementString: String) {
    Row(
        modifier = Modifier.padding(start = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(width = 8.dp, height = 2.dp)
                .clip(RectangleShape)
                .align(Alignment.CenterVertically)
                .background(color = Color.Black)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = elementString,
            style = heading3TextStyle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    var uiState = SearchUiState.Success(
        searchQuery = "",
        history = emptySet(),
        isSubmitting = false,
        searchResultResponse = null,
    )
    SearchScreen(
        state = uiState,
        onSearchQueryChange = { query -> uiState = uiState.copy(searchQuery = query) },
        onSearch = {}
    )
}