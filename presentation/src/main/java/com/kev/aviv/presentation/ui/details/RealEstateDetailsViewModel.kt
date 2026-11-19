package com.kev.aviv.presentation.ui.details

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
import com.kev.aviv.domain.usecase.GetRealEstateDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealEstateDetailsViewModel @Inject constructor(
    private val getRealEstateDetailsUseCase: GetRealEstateDetailsUseCase
) : BaseViewModel<RealEstateDetailsState>() {
    override fun createInitialState(): RealEstateDetailsState = RealEstateDetailsState()

    /** Request of the listing currently displayed; cancelled when another one is requested. */
    private var detailsJob: Job? = null

    fun fetchRealEstateDetails(id: String) {
        // On tablets the detail pane stays on screen while the user taps other listings: a slow
        // answer for a previous listing must not replace the one requested last.
        detailsJob?.cancel()
        detailsJob = viewModelScope.launch {
            getRealEstateDetailsUseCase(id).asResult()
                .doOnLoading {
                    setState { copy(loaderState = LoaderState.IN_PROGRESS) }
                }
                .doOnSuccess {
                    setState { copy(details = it, loaderState = LoaderState.SUCCEED) }
                }
                .doOnFailure { error ->
                    setState { copy(loaderState = LoaderState.FAILED, errorType = error.toErrorType()) }
                }
                .collect()
        }
    }
}

data class RealEstateDetailsState(
    val details: RealEstateInfosDomain? = null,
    val loaderState: LoaderState = LoaderState.IN_PROGRESS,
    val errorType: ErrorType? = null
) : UiState