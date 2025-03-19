package com.kev.aviv.data.datasource

import com.kev.aviv.common.network.NetworkResponse
import com.kev.aviv.data.model.RealEstateInfosDTO
import com.kev.aviv.data.model.RealEstateListingsResponse

/**
 * Interface defining the data source for Pokémon-related data.
 * The implementation of this interface will provide methods to fetch Pokémon data
 * either as a list of all Pokémon or the details of a specific Pokémon.
 */
interface RealEstateDataSource {

    /**
     * Fetches a list of all real estate.
     * This method should be implemented to make the appropriate network call
     * and return the results as a [NetworkResponse] wrapped in a [RealEstateListingsResponse].
     *
     * @return A [NetworkResponse] containing a [RealEstateListingsResponse] with the list of real estate.
     */
    suspend fun getRealEstateListings(): NetworkResponse<RealEstateListingsResponse>

    /**
     * Fetches the details of a specific real estate by its ID.
     * This method should be implemented to make the appropriate network call
     * and return the results as a [NetworkResponse] wrapped in a [RealEstateInfosDTO]
     *
     * @param id The ID of the real estate to fetch details for.
     * @return A [NetworkResponse] containing a [RealEstateInfosDTO] with the details of the specified real estate.
     *
     */
    suspend fun getRealEstateDetails(id: String): NetworkResponse<RealEstateInfosDTO>

}