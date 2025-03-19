package com.kev.aviv.domain.repository

import com.kev.aviv.domain.model.RealEstateInfosDomain
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining the repository methods for fetching real estate data.
 * This interface abstracts the data layer and is used by use cases to interact
 * with the data source(s) without knowing the details of the data retrieval.
 */
interface RealEstateRepository {

    /**
     * Fetches a list of all real estate.
     *
     * @return A [Flow] emitting a list of [RealEstateInfosDomain] containing the list of real estate.
     */
    suspend fun getRealEstateListings(): Flow<List<RealEstateInfosDomain>>

    /**
     * Fetches the details of a specific real estate by its ID.
     *
     * @param id The ID of the real estate to fetch details for.
     * @return A [Flow] emitting a [RealEstateInfosDomain] containing the details of the specified real estate.
     */
    suspend fun getRealEstateDetails(id: String): Flow<RealEstateInfosDomain>
}