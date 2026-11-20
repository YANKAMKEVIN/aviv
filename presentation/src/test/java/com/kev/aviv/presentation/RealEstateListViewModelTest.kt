package com.kev.aviv.presentation

import com.kev.aviv.common.loader.ErrorType
import com.kev.aviv.common.loader.LoaderState
import com.kev.aviv.common.network.ApiException
import com.kev.aviv.common.network.NetworkError
import com.kev.aviv.domain.model.RealEstateInfosDomain
import com.kev.aviv.domain.usecase.GetRealEstatesListUseCase
import com.kev.aviv.presentation.ui.list.RealEstateListViewModel
import com.kev.aviv.testing.utils.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CompletableDeferred
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

    @Before
    fun setup() {
        MockKAnnotations.init(this)
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

    @Test
    fun `offline failure exposes a network error`() = runTest {
        coEvery { getRealEstatesListUseCase() } returns flow {
            throw ApiException(NetworkError.UnResolveAddress("Unable to resolve host"))
        }

        val viewModel = RealEstateListViewModel(getRealEstatesListUseCase)
        advanceUntilIdle()

        assert(viewModel.state.value.loaderState == LoaderState.FAILED)
        assert(viewModel.state.value.errorType == ErrorType.NETWORK)
    }

    @Test
    fun `server failure exposes a server error`() = runTest {
        coEvery { getRealEstatesListUseCase() } returns flow {
            throw ApiException(NetworkError.InternalServerError("Internal server error"))
        }

        val viewModel = RealEstateListViewModel(getRealEstatesListUseCase)
        advanceUntilIdle()

        assert(viewModel.state.value.errorType == ErrorType.SERVER)
    }

    private val paris = RealEstateInfosDomain(
        id = 1, city = "Paris", area = 100.0, price = 1750000.0, professional = "Agence XYZ",
        propertyType = "Appartement", offerType = 1, bedrooms = 3, rooms = 5, imageUrl = null
    )
    private val lyon = paris.copy(id = 2, city = "Lyon")

    @Test
    fun `refresh keeps the listings on screen while reloading`() = runTest {
        val answer = CompletableDeferred<List<RealEstateInfosDomain>>()
        coEvery { getRealEstatesListUseCase() } returnsMany listOf(
            flowOf(listOf(paris)),
            flow { emit(answer.await()) }
        )
        val viewModel = RealEstateListViewModel(getRealEstatesListUseCase)
        advanceUntilIdle()

        viewModel.refresh()

        assert(viewModel.state.value.isRefreshing)
        assert(viewModel.state.value.loaderState == LoaderState.SUCCEED)
        assert(viewModel.state.value.realEstates == listOf(paris))

        answer.complete(listOf(paris, lyon))
        advanceUntilIdle()

        assert(!viewModel.state.value.isRefreshing)
        assert(viewModel.state.value.realEstates == listOf(paris, lyon))
    }

    @Test
    fun `a failed refresh keeps the listings already shown`() = runTest {
        coEvery { getRealEstatesListUseCase() } returnsMany listOf(
            flowOf(listOf(paris)),
            flow { throw ApiException(NetworkError.UnResolveAddress("Unable to resolve host")) }
        )
        val viewModel = RealEstateListViewModel(getRealEstatesListUseCase)
        advanceUntilIdle()

        viewModel.refresh()
        advanceUntilIdle()

        assert(!viewModel.state.value.isRefreshing)
        assert(viewModel.state.value.loaderState == LoaderState.SUCCEED)
        assert(viewModel.state.value.realEstates == listOf(paris))
    }

    @Test
    fun `a failed refresh with nothing to show displays the error`() = runTest {
        coEvery { getRealEstatesListUseCase() } returnsMany listOf(
            flowOf(emptyList()),
            flow { throw ApiException(NetworkError.InternalServerError("Internal server error")) }
        )
        val viewModel = RealEstateListViewModel(getRealEstatesListUseCase)
        advanceUntilIdle()

        viewModel.refresh()
        advanceUntilIdle()

        assert(viewModel.state.value.loaderState == LoaderState.FAILED)
        assert(viewModel.state.value.errorType == ErrorType.SERVER)
    }
}
