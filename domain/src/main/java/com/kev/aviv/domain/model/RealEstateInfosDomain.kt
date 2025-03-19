package com.kev.aviv.domain.model

data class RealEstateInfosDomain(
    val id: Int,
    val city: String,
    val area: Double,
    val price: Double,
    val professional: String,
    val propertyType: String,
    val offerType: Int,
    val bedrooms: Int?,
    val rooms: Int?,
    val imageUrl: String?
)