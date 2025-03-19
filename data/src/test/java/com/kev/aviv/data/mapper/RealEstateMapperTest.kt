package com.kev.aviv.data.mapper

import com.kev.aviv.data.mapper.RealEstateMapper.toDomain
import com.kev.aviv.data.model.RealEstateInfosDTO
import com.kev.aviv.domain.model.RealEstateInfosDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class RealEstateMapperTest {

    @Test
    fun `mapToDomain should correctly map RealEstateInfosDTO to RealEstateInfosDomain`() {
        // Arrange: Create RealEstateInfosDTO with test data
        val realEstate1 =
            RealEstateInfosDTO(
                id = 1,
                city = "Paris",
                area = 100.0,
                price = 1750000.0,
                professional = "Agence XYZ",
                propertyType = "Appartement",
                offerType = 1,
                bedrooms = 3,
                rooms = 5,
                url = "https://v.seloger.com/s/crop/590x3"
            )

        // Act: Map the response to a domain object
        val result: RealEstateInfosDomain = realEstate1.toDomain()

        // Assert: Verify the mapping results
        assertEquals(1, result.id)
        assertEquals("Paris", result.city)
        assertEquals(100.0, result.area, 0.1)
        assertEquals(1750000.0, result.price, 0.1)
        assertEquals("Agence XYZ", result.professional)
        assertEquals("Appartement", result.propertyType)
        assertEquals(1, result.offerType)
        assertEquals(3, result.bedrooms)
        assertEquals(5, result.rooms)
        assertEquals("https://v.seloger.com/s/crop/590x3", result.imageUrl)

    }
}