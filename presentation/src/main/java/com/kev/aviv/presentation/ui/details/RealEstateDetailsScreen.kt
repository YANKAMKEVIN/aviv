package com.kev.aviv.presentation.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kev.aviv.common.loader.LoaderScreen
import com.kev.aviv.common.loader.ReloadScreen

@Composable
fun RealEstateDetailsRoute(
    id: String,
    viewModel: RealEstateDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.fetchRealEstateDetails(id)
    }

    LoaderScreen(
        state = state.loaderState,
        onRetry = { viewModel.fetchRealEstateDetails(id) },
        screenComposable = {
            RealEstateDetailsScreen(state = state) {
                viewModel.fetchRealEstateDetails(id)
            }
        }
    )
}

@Composable
fun RealEstateDetailsScreen(
    state: RealEstateDetailsState,
    onRetry: () -> Unit = {}
) {
    if (state.details != null)
        RealEstateDetailsItem(state.details)
    else
        ReloadScreen(onRetry)
}

@Composable
@Preview(showBackground = true)
fun RealEstateDetailsPreview() {
    RealEstateDetailsScreen(state = RealEstateDetailsState())
}