package com.kev.aviv.domain.usecase

import app.cash.turbine.test
import com.kev.aviv.domain.model.RealEstateInfosDomain
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetRealEstateDetailsUseCaseTest {

    private val fakeRealEstate = RealEstateInfosDomain(
        id = 1,
        city = "Paris",
        area = 100.0,
        price = 750000.0,
        professional = "Agence XYZ",
        propertyType = "Appartement",
        offerType = 1,
        bedrooms = 3,
        rooms = 5,
        imageUrl = "https://via.placeholder.com/300"
    )

    private val useCase: GetRealEstateDetailsUseCase = mockk()

    @Test
    fun `invoke should return real estate details`() = runTest {
        // GIVEN
        val expectedId = "1"
        coEvery { useCase.invoke(expectedId) } returns flowOf(fakeRealEstate)

        // WHEN
        useCase.invoke(expectedId).test {
            val result = awaitItem()
            assertEquals(fakeRealEstate, result)
            awaitComplete()
        }
    }
}
