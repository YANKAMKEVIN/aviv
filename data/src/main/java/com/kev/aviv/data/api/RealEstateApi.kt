package com.kev.aviv.data.api

import com.kev.aviv.data.model.RealEstateInfosDTO
import com.kev.aviv.data.model.RealEstateListingsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface RealEstateApi {
    @GET("listings.json")
    suspend fun getRealEstateListings(): RealEstateListingsResponse

    @GET("listings/{id}.json")
    suspend fun getRealEstateDetails(@Path("id") id: String): RealEstateInfosDTO
}