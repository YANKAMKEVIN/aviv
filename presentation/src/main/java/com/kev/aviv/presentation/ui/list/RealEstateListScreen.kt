package com.kev.aviv.presentation.ui.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RealEstateListRoute(
    viewModel: RealEstateListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RealEstateListScreen(state)
}

@Composable
fun RealEstateListScreen(
    state: RealEstateListState
) {
    LazyColumn() {
        items(state.realEstates) { realEstate ->
            RealEstateItem(realEstate)
        }
    }
}

@Composable
@Preview
fun RealEstateListScreenPreview() {
    RealEstateListScreen(
        state = RealEstateListState()
    )
}