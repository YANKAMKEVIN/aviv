package com.kev.aviv.presentation

import com.kev.aviv.common.loader.LoaderState
import com.kev.aviv.domain.model.RealEstateInfosDomain
import com.kev.aviv.domain.usecase.GetRealEstateDetailsUseCase
import com.kev.aviv.presentation.ui.details.RealEstateDetailsViewModel
import com.kev.aviv.testing.utils.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class RealEstateDetailsViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var getDetailsUseCase: GetRealEstateDetailsUseCase

    private lateinit var viewModel: RealEstateDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = RealEstateDetailsViewModel(getDetailsUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetchRealEstateDetails success should update state correctly`() = runTest {
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

        coEvery { getDetailsUseCase(any()) } returns flowOf(realEstate1)

        //When
        viewModel.fetchRealEstateDetails("1")

        //Then
        assert(viewModel.state.value.details == realEstate1)
        assert(viewModel.state.value.loaderState == LoaderState.SUCCEED)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetchRealEstateDetails fail should update state correctly`() = runTest {
        //Given
        coEvery { getDetailsUseCase(any()) } returns flow {
            throw Exception("An error occurred")
        }

        //When
        viewModel.fetchRealEstateDetails("1")

        //Then
        assert(viewModel.state.value.loaderState == LoaderState.FAILED)
    }
}