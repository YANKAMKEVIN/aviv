package com.kev.aviv.data.model

data class RealEstateListingsResponse (
    val items: List<RealEstateInfosDTO>,
    val totalCount:Int
)