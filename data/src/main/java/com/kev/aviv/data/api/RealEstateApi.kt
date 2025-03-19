package com.kev.aviv.data.api

import com.kev.aviv.data.model.RealEstateListingsResponse
import retrofit2.http.GET

interface RealEstateApi {
    @GET("listings.json")
    suspend fun getRealEstateListings(): RealEstateListingsResponse
}