package com.kev.aviv.presentation.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kev.aviv.common.loader.LoaderScreen

@Composable
fun RealEstateListRoute(
    onNavigateToDetail: (id: String) -> Unit,
    viewModel: RealEstateListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoaderScreen(
        state = state.loaderState,
        onRetry = viewModel::fetchRealEstatesList,
        errorType = state.errorType,
        screenComposable = {
            RealEstateListScreen(
                state = state,
                onNavigateToDetail = onNavigateToDetail,
                onRefresh = viewModel::fetchRealEstatesList
            )
        }
    )
}

@Composable
fun RealEstateListScreen(
    state: RealEstateListState,
    onNavigateToDetail: (id: String) -> Unit,
    onRefresh: () -> Unit = {}
) {
    Column {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(com.kev.aviv.common.R.string.list_screen_title),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (state.realEstates.isEmpty()) {
            RealEstateEmptyList(onRefresh = onRefresh)
            return@Column
        }
        LazyColumn {
            items(state.realEstates) { realEstate ->
                RealEstateItem(realEstate, onNavigateToDetail)
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .height(1.dp)
                        .background(Color.Blue)

                )
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