package com.kev.aviv.data.mapper

import com.kev.aviv.data.model.RealEstateInfosDTO
import com.kev.aviv.domain.model.RealEstateInfosDomain

object RealEstateMapper {

    //Map RealEstateInfosDTO to RealEstateInfosDomain
    fun RealEstateInfosDTO.toDomain(): RealEstateInfosDomain{
        return RealEstateInfosDomain(
            id = id,
            city = city,
            area = area,
            price = price,
            professional = professional,
            propertyType = propertyType,
            offerType = offerType,
            bedrooms = bedrooms,
            rooms = rooms,
            imageUrl = url
        )
    }
}