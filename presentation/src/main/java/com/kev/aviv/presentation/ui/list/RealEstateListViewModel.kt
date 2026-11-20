package com.kev.aviv.presentation.ui.list

import androidx.lifecycle.viewModelScope
import com.kev.aviv.common.base.BaseViewModel
import com.kev.aviv.common.base.UiState
import com.kev.aviv.common.loader.ErrorType
import com.kev.aviv.common.loader.LoaderState
import com.kev.aviv.common.loader.toErrorType
import com.kev.aviv.common.result.asResult
import com.kev.aviv.common.result.doOnFailure
import com.kev.aviv.common.result.doOnLoading
import com.kev.aviv.common.result.doOnSuccess
import com.kev.aviv.domain.model.RealEstateInfosDomain
import com.kev.aviv.domain.usecase.GetRealEstatesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealEstateListViewModel @Inject constructor(
    private val getRealEstatesListUseCase: GetRealEstatesListUseCase
) : BaseViewModel<RealEstateListState>() {
    override fun createInitialState(): RealEstateListState = RealEstateListState()

    /** Current request; a new one (retry, refresh) replaces it. */
    private var listJob: Job? = null

    init {
        fetchRealEstatesList()
    }

    /** Full-screen load: first launch, "Réessayer" and "Actualiser". */
    fun fetchRealEstatesList() {
        listJob?.cancel()
        listJob = viewModelScope.launch {
            getRealEstatesListUseCase().asResult()
                .doOnLoading {
                    setState { copy(loaderState = LoaderState.IN_PROGRESS) }
                }
                .doOnSuccess {
                    setState { copy(realEstates = it, loaderState = LoaderState.SUCCEED, isRefreshing = false) }
                }
                .doOnFailure { error ->
                    setState {
                        copy(loaderState = LoaderState.FAILED, errorType = error.toErrorType(), isRefreshing = false)
                    }
                }
                .collect()
        }
    }

    /**
     * Pull-to-refresh: reloads while the current listings stay on screen. If it fails, the
     * listings already shown are kept; the error screen is only used when there is nothing to show.
     */
    fun refresh() {
        if (currentState.isRefreshing) return
        listJob?.cancel()
        listJob = viewModelScope.launch {
            getRealEstatesListUseCase().asResult()
                .doOnLoading {
                    setState { copy(isRefreshing = true) }
                }
                .doOnSuccess {
                    setState { copy(realEstates = it, loaderState = LoaderState.SUCCEED, isRefreshing = false) }
                }
                .doOnFailure { error ->
                    setState {
                        if (realEstates.isEmpty()) {
                            copy(loaderState = LoaderState.FAILED, errorType = error.toErrorType(), isRefreshing = false)
                        } else {
                            copy(isRefreshing = false)
                        }
                    }
                }
                .collect()
        }
    }
}

data class RealEstateListState(
    val realEstates: List<RealEstateInfosDomain> = emptyList(),
    val loaderState: LoaderState = LoaderState.IN_PROGRESS,
    val errorType: ErrorType? = null,
    /** A pull-to-refresh is in progress (the current listings stay displayed). */
    val isRefreshing: Boolean = false
) : UiState