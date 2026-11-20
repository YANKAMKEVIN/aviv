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
    onBack: (() -> Unit)? = null,
    viewModel: RealEstateDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(id) {
        viewModel.fetchRealEstateDetails(id)
    }

    LoaderScreen(
        state = state.loaderState,
        onRetry = { viewModel.fetchRealEstateDetails(id) },
        errorType = state.errorType,
        screenComposable = {
            RealEstateDetailsScreen(
                state = state,
                onBack = onBack,
                onRetry = { viewModel.fetchRealEstateDetails(id) }
            )
        }
    )
}

@Composable
fun RealEstateDetailsScreen(
    state: RealEstateDetailsState,
    onBack: (() -> Unit)? = null,
    onRetry: () -> Unit = {}
) {
    if (state.details != null)
        RealEstateDetailsItem(state.details, onBack)
    else
        ReloadScreen(onRetry)
}

@Composable
@Preview(showBackground = true)
fun RealEstateDetailsPreview() {
    RealEstateDetailsScreen(state = RealEstateDetailsState())
}