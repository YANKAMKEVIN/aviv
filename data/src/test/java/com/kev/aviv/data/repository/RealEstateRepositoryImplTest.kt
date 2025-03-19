package com.kev.aviv.data.repository

import com.kev.aviv.common.network.NetworkError
import com.kev.aviv.common.network.NetworkResponse
import com.kev.aviv.data.datasource.RealEstateDataSource
import com.kev.aviv.data.model.RealEstateInfosDTO
import com.kev.aviv.data.model.RealEstateListingsResponse
import com.kev.aviv.domain.model.RealEstateInfosDomain
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RealEstateRepositoryImplTest {

    private lateinit var repository: RealEstateRepositoryImpl
    private val dataSource: RealEstateDataSource = mockk()

    @Before
    fun setup() {
        repository = RealEstateRepositoryImpl(dataSource)
    }

    @Test
    fun `getRealEstateListings returns list of real estate`() = runBlocking {
        // Given: Fake data
        val fakeDataDomain = listOf(
            RealEstateInfosDomain(
                id = 1,
                city = "Paris",
                area = 100.0,
                price = 750000.0,
                professional = "XYZ",
                propertyType = "Appartement",
                offerType = 1,
                bedrooms = 3,
                rooms = 5,
                imageUrl = ""
            ),
            RealEstateInfosDomain(
                id = 2,
                city = "Lyon",
                area = 80.0,
                price = 500000.0,
                professional = "ABC",
                propertyType = "Maison",
                offerType = 2,
                bedrooms = 2,
                rooms = 4,
                imageUrl = ""
            )
        )
        val fakeDataDTO = listOf(
            RealEstateInfosDTO(
                id = 1,
                city = "Paris",
                area = 100.0,
                price = 750000.0,
                professional = "XYZ",
                propertyType = "Appartement",
                offerType = 1,
                bedrooms = 3,
                rooms = 5,
                url = ""
            ),
            RealEstateInfosDTO(
                id = 2,
                city = "Lyon",
                area = 80.0,
                price = 500000.0,
                professional = "ABC",
                propertyType = "Maison",
                offerType = 2,
                bedrooms = 2,
                rooms = 4,
                url = ""
            )
        )
        val fakeResponse = RealEstateListingsResponse(fakeDataDTO, 2)

        coEvery { dataSource.getRealEstateListings() } returns NetworkResponse.Success(fakeResponse)

        // When
        val result = repository.getRealEstateListings().first()

        // Then
        assertEquals(fakeDataDomain, result)
        coVerify { dataSource.getRealEstateListings() }
    }

    @Test
    fun `getRealEstateDetails returns real estate`() = runBlocking {
        // Given: Fake data
        val fakeDataDomain = RealEstateInfosDomain(
            id = 2,
            city = "Lyon",
            area = 80.0,
            price = 500000.0,
            professional = "ABC",
            propertyType = "Maison",
            offerType = 2,
            bedrooms = 2,
            rooms = 4,
            imageUrl = ""
        )

        val fakeDataDTO = RealEstateInfosDTO(
            id = 2,
            city = "Lyon",
            area = 80.0,
            price = 500000.0,
            professional = "ABC",
            propertyType = "Maison",
            offerType = 2,
            bedrooms = 2,
            rooms = 4,
            url = ""
        )

        coEvery { dataSource.getRealEstateDetails(any()) } returns NetworkResponse.Success(
            fakeDataDTO
        )

        // When
        val result = repository.getRealEstateDetails("1").first()

        // Then
        assertEquals(fakeDataDomain, result)
        coVerify { dataSource.getRealEstateDetails("1") }
    }

    @Test
    fun `getRealEstateListings throws exception when network fails with BadRequest`() =
        runBlocking {
            // Given
            val networkError = NetworkError.BadRequest("Bad request error")
            coEvery { dataSource.getRealEstateListings() } returns NetworkResponse.Failure(
                networkError
            )

            // When & Then
            try {
                repository.getRealEstateListings().first()
                assert(false) { "Exception was expected" }
            } catch (e: Exception) {
                assertEquals("Error Bad request error: 400", e.message)
            }

            coVerify { dataSource.getRealEstateListings() }
        }

    @Test
    fun `getRealEstateListings throws exception when network fails with Unauthorized`() =
        runBlocking {
            // Given
            val networkError = NetworkError.Unauthorized("Unauthorized access")
            coEvery { dataSource.getRealEstateListings() } returns NetworkResponse.Failure(
                networkError
            )

            // When & Then
            try {
                repository.getRealEstateListings().first()
                assert(false) { "Exception was expected" }
            } catch (e: Exception) {
                assertEquals("Error Unauthorized access: 401", e.message)
            }

            coVerify { dataSource.getRealEstateListings() }
        }

    @Test
    fun `getRealEstateListings throws exception when network fails with InternalServerError`() =
        runBlocking {
            // Given
            val networkError = NetworkError.InternalServerError("Internal server error")
            coEvery { dataSource.getRealEstateListings() } returns NetworkResponse.Failure(
                networkError
            )

            // When & Then
            try {
                repository.getRealEstateListings().first()
                assert(false) { "Exception was expected" }
            } catch (e: Exception) {
                assertEquals("Error Internal server error: 500", e.message)
            }

            coVerify { dataSource.getRealEstateListings() }
        }

    @Test
    fun `getRealEstateDetails throws exception when network fails with NotFound`() = runBlocking {
        // Given
        val networkError = NetworkError.NotFound("Real estate not found")
        coEvery { dataSource.getRealEstateDetails(any()) } returns NetworkResponse.Failure(
            networkError
        )

        // When & Then
        try {
            repository.getRealEstateDetails("1").first()
            assert(false) { "Exception was expected" }
        } catch (e: Exception) {
            assertEquals("Error Real estate not found: 404", e.message)
        }

        coVerify { dataSource.getRealEstateDetails("1") }
    }

    @Test
    fun `getRealEstateDetails throws exception when network fails with Unknown`() = runBlocking {
        // Given
        val networkError = NetworkError.Unknown("Unknown error")
        coEvery { dataSource.getRealEstateDetails(any()) } returns NetworkResponse.Failure(
            networkError
        )

        // When & Then
        try {
            repository.getRealEstateDetails("1").first()
            assert(false) { "Exception was expected" }
        } catch (e: Exception) {
            assertEquals("Error Unknown error: -2", e.message)
        }

        coVerify { dataSource.getRealEstateDetails("1") }
    }

}
