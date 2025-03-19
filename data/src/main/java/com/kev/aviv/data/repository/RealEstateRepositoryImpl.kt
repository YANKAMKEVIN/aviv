package com.kev.aviv.data.repository

import com.kev.aviv.common.network.NetworkResponse
import com.kev.aviv.data.datasource.RealEstateDataSource
import com.kev.aviv.data.mapper.RealEstateMapper.toDomain
import com.kev.aviv.domain.model.RealEstateInfosDomain
import com.kev.aviv.domain.repository.RealEstateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation of the [RealEstateRepository] interface.
 * This class is responsible for processing the data retrieved from the [RealEstateDataSource]
 * and transforming it into the domain model before passing it to the use cases.
 */
class RealEstateRepositoryImpl @Inject constructor(
    private val realEstateDataSource: RealEstateDataSource
) : RealEstateRepository {

    /**
     * Fetches a list of all real estate from the data source and maps the result into the domain model.
     * The result is emitted as a [Flow] containing a list of [RealEstateInfosDomain].
     *
     * @return A [Flow] emitting a list of [RealEstateInfosDomain] containing the list of real estate.
     */
    override suspend fun getRealEstateListings(): Flow<List<RealEstateInfosDomain>> = flow {
        when (val response = realEstateDataSource.getRealEstateListings()) {
            is NetworkResponse.Success -> {
                val realEstateListings = response.data.items.map { it.toDomain() }
                emit(realEstateListings)
            }
            is NetworkResponse.Failure -> {
                throw Exception("Error ${response.error.message}: ${response.error.code}")
            }
        }
    }
}