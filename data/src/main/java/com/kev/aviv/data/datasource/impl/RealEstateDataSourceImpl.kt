package com.kev.aviv.data.datasource.impl

import com.kev.aviv.common.network.NetworkResponse
import com.kev.aviv.common.network.NetworkUtil
import com.kev.aviv.data.api.RealEstateApi
import com.kev.aviv.data.datasource.RealEstateDataSource
import com.kev.aviv.data.model.RealEstateListingsResponse
import javax.inject.Inject


/**
 * Implementation of the [RealEstateDataSource] interface.
 * This class is responsible for fetching Pokémon data from the [RealEstateApi]
 * and wrapping the responses in a [NetworkResponse].
 *
 * @param realEstateApi The API service used to fetch data.
 */

class RealEstateDataSourceImpl @Inject constructor(
    private val realEstateApi: RealEstateApi
) : RealEstateDataSource {

    /**
     * Fetches a list of all real estate from the API.
     * This method wraps the API response in a [NetworkResponse] to handle success or failure.
     *
     * @return A [NetworkResponse] wrapping a [RealEstateListingsResponse] containing the list of real estate.
     */
    override suspend fun getRealEstateListings(): NetworkResponse<RealEstateListingsResponse> =
        NetworkUtil.executeApiCall {
            realEstateApi.getRealEstateListings()
        }
}