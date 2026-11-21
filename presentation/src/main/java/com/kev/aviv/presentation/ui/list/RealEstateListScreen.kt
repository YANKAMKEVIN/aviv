package com.kev.aviv.presentation.ui.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kev.aviv.common.loader.LoaderScreen
import com.kev.aviv.common.loader.LoaderState
import com.kev.aviv.presentation.ui.RealEstateListSkeleton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealEstateListRoute(
    onNavigateToDetail: (id: String) -> Unit,
    selectedId: String? = null,
    viewModel: RealEstateListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    // A failed pull-to-refresh keeps the listings: say so, and offer to try again.
    state.refreshError?.let { refreshError ->
        LaunchedEffect(refreshError) {
            val result = snackbarHostState.showSnackbar(
                message = context.getString(refreshError.refreshErrorMessage()),
                actionLabel = context.getString(com.kev.aviv.common.R.string.error_screen_button_text),
                duration = SnackbarDuration.Long
            )
            viewModel.onRefreshErrorShown()
            if (result == SnackbarResult.ActionPerformed) viewModel.refresh()
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        // The header stays on screen while loading, on error and when the list is empty.
        topBar = {
            RealEstateListTopBar(
                count = state.realEstates.size.takeIf { state.loaderState == LoaderState.SUCCEED },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LoaderScreen(
                state = state.loaderState,
                onRetry = viewModel::fetchRealEstatesList,
                errorType = state.errorType,
                loadingContent = { RealEstateListSkeleton() },
                screenComposable = {
                    RealEstateListScreen(
                        state = state,
                        onNavigateToDetail = onNavigateToDetail,
                        onRefresh = viewModel::fetchRealEstatesList,
                        onPullRefresh = viewModel::refresh,
                        selectedId = selectedId
                    )
                }
            )
        }
    }
}

/** App bar of the list: logotype and number of listings; it hides while scrolling down. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealEstateListTopBar(
    count: Int?,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = stringResource(com.kev.aviv.common.R.string.list_screen_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
                if (count != null && count > 0) {
                    Text(
                        text = pluralStringResource(com.kev.aviv.common.R.plurals.list_screen_count, count, count),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealEstateListScreen(
    state: RealEstateListState,
    onNavigateToDetail: (id: String) -> Unit,
    onRefresh: () -> Unit = {},
    onPullRefresh: () -> Unit = {},
    selectedId: String? = null
) {
    Column {
        if (state.realEstates.isEmpty()) {
            RealEstateEmptyList(onRefresh = onRefresh)
            return@Column
        }
        PullToRefreshBox(
            isRefreshing = state.isRefreshing,
            onRefresh = onPullRefresh,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.realEstates, key = { it.id }) { realEstate ->
                    RealEstateItem(
                        realEstate = realEstate,
                        onClick = onNavigateToDetail,
                        isSelected = realEstate.id.toString() == selectedId
                    )
                }
            }
        }
    }
}

@Composable
private fun RealEstateEmptyList(onRefresh: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(com.kev.aviv.common.R.drawable.empty_state),
            contentDescription = null,
            modifier = Modifier.size(96.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(com.kev.aviv.common.R.string.list_screen_empty_title),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(com.kev.aviv.common.R.string.list_screen_empty_description),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedButton(onClick = onRefresh) {
            Text(text = stringResource(com.kev.aviv.common.R.string.list_screen_empty_refresh))
        }
    }
}

@Composable
@Preview
fun RealEstateListScreenPreview() {
    RealEstateListScreen(
        state = RealEstateListState(),
        onNavigateToDetail = {}
    )
}