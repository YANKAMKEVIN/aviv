package com.kev.aviv.domain.usecase

import app.cash.turbine.test
import com.kev.aviv.domain.model.RealEstateInfosDomain
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetRealEstatesListUseCaseTest {

    private val fakeRealEstates = listOf(
        RealEstateInfosDomain(
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
        ),
        RealEstateInfosDomain(
            id = 2,
            city = "Lyon",
            area = 80.0,
            price = 500000.0,
            professional = "Agence ABC",
            propertyType = "Maison",
            offerType = 2,
            bedrooms = 2,
            rooms = 4,
            imageUrl = "https://via.placeholder.com/300"
        )
    )

    private val useCase: GetRealEstatesListUseCase = mockk()

    @Test
    fun `invoke should return list of real estates`() = runTest {
        // GIVEN
        coEvery { useCase.invoke() } returns flowOf(fakeRealEstates)

        // WHEN
        useCase.invoke().test {
            val result = awaitItem()
            assertEquals(fakeRealEstates, result)
            awaitComplete()
        }
    }
}
