package com.kev.aviv.presentation.ui.list

import androidx.lifecycle.viewModelScope
import com.kev.aviv.common.base.BaseViewModel
import com.kev.aviv.common.base.UiState
import com.kev.aviv.common.result.asResult
import com.kev.aviv.common.result.doOnFailure
import com.kev.aviv.common.result.doOnLoading
import com.kev.aviv.common.result.doOnSuccess
import com.kev.aviv.domain.model.RealEstateInfosDomain
import com.kev.aviv.domain.usecase.GetRealEstatesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealEstateListViewModel @Inject constructor(
    private val getRealEstatesListUseCase: GetRealEstatesListUseCase
) : BaseViewModel<RealEstateListState>() {
    override fun createInitialState(): RealEstateListState = RealEstateListState()

    init {
        getRealEstatesList()
    }

    private fun getRealEstatesList() {
        viewModelScope.launch {
            getRealEstatesListUseCase().asResult()
                .doOnLoading {
                    setState { copy(isLoading = true) }
                }
                .doOnSuccess {
                    setState { copy(realEstates = it, isLoading = false) }
                }
                .doOnFailure {
                    setState { copy(isLoading = false) }
                }
                .collect()
        }
    }
}

data class RealEstateListState(
    val realEstates: List<RealEstateInfosDomain> = emptyList(),
    val isLoading: Boolean = false
) : UiState