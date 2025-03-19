package com.kev.aviv.data.datasource

import com.kev.aviv.common.network.NetworkError
import com.kev.aviv.common.network.NetworkResponse
import com.kev.aviv.data.api.RealEstateApi
import com.kev.aviv.data.datasource.impl.RealEstateDataSourceImpl
import com.kev.aviv.data.model.RealEstateInfosDTO
import com.kev.aviv.data.model.RealEstateListingsResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RealEstateDataSourceTest {

    private lateinit var dataSource: RealEstateDataSource
    private lateinit var realEstateApi: RealEstateApi

    @Before
    fun setUp() {
        realEstateApi = mockk()
        dataSource = RealEstateDataSourceImpl(realEstateApi)
    }

    @Test
    fun `should return success response when getRealEstateListings is successfully called`() =
        runBlocking {
            //Arrange
            val realEstate2 =
                RealEstateInfosDTO(
                    id = 2,
                    city = "Londres",
                    area = 150.0,
                    price = 450000.0,
                    professional = "Agence ABC",
                    propertyType = "Maison",
                    offerType = 2,
                    bedrooms = 5,
                    rooms = 7,
                    url = "https://v.seloger.com/s/crop/590x8"
                )

            val mockResponse = RealEstateListingsResponse(
                items = listOf(realEstate2),
                totalCount = 1
            )

            coEvery { realEstateApi.getRealEstateListings() } returns mockResponse

            // Act
            val result = dataSource.getRealEstateListings()

            // Assert
            assertTrue(result is NetworkResponse.Success)
            assertEquals(mockResponse, result.data)
        }

    @Test
    fun `should return failure response when getRealEstateListings fails with unknown error`() =
        runBlocking {
            // Arrange
            val errorMessage = "Unknown error"
            coEvery { realEstateApi.getRealEstateListings() } throws Exception(errorMessage)
            // Act
            val result = dataSource.getRealEstateListings()
            // Assert
            assertTrue(result is NetworkResponse.Failure)
            assertTrue(result.error is NetworkError.Unknown)
            assertEquals(errorMessage, result.error.message)
        }

}