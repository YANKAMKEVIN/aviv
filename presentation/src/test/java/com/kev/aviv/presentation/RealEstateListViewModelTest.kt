package com.kev.aviv.presentation

import com.kev.aviv.common.loader.LoaderState
import com.kev.aviv.domain.model.RealEstateInfosDomain
import com.kev.aviv.domain.usecase.GetRealEstatesListUseCase
import com.kev.aviv.presentation.ui.list.RealEstateListViewModel
import com.kev.aviv.testing.utils.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RealEstateListViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var getRealEstatesListUseCase: GetRealEstatesListUseCase

    private lateinit var viewModel: RealEstateListViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        //viewModel = RealEstateListViewModel(getRealEstatesListUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetchRealEstatesList success should update state correctly`() = runTest {
        //Given
        val realEstate1 = RealEstateInfosDomain(
            id = 1,
            city = "Paris",
            area = 100.0,
            price = 1750000.0,
            professional = "Agence XYZ",
            propertyType = "Appartement",
            offerType = 1,
            bedrooms = 3,
            rooms = 5,
            imageUrl = "https://v.seloger.com/s/crop/590x3"
        )

        val realEstate2 = RealEstateInfosDomain(
            id = 2,
            city = "Londres",
            area = 150.0,
            price = 450000.0,
            professional = "Agence ABC",
            propertyType = "Maison",
            offerType = 2,
            bedrooms = 5,
            rooms = 7,
            imageUrl = "https://v.seloger.com/s/crop/590x8"
        )

        val realEstates = listOf(realEstate1, realEstate2)

        coEvery { getRealEstatesListUseCase() } returns flowOf(realEstates)

        //When
        val viewModel = RealEstateListViewModel(getRealEstatesListUseCase)
        advanceUntilIdle()

        //Then
        assert(viewModel.state.value.realEstates == realEstates)
        assert(viewModel.state.value.loaderState == LoaderState.SUCCEED)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetchRealEstatesList success with empty list should update state correctly`() = runTest {
        //Given
        val realEstates = emptyList<RealEstateInfosDomain>()

        coEvery { getRealEstatesListUseCase() } returns flowOf(realEstates)

        //When
        val viewModel = RealEstateListViewModel(getRealEstatesListUseCase)
        advanceUntilIdle()

        //Then
        assert(viewModel.state.value.realEstates == realEstates)
        assert(viewModel.state.value.loaderState == LoaderState.SUCCEED)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetchRealEstatesList fail should update state correctly`() = runTest {
        //Given

        coEvery { getRealEstatesListUseCase() } returns flow {
            throw Exception("An error occurred")
        }

        //When
        val viewModel = RealEstateListViewModel(getRealEstatesListUseCase)
        advanceUntilIdle()

        //Then
        assert(viewModel.state.value.loaderState == LoaderState.FAILED)
    }
}