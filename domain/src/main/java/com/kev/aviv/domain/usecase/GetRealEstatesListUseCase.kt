package com.kev.aviv.domain.usecase

import com.kev.aviv.domain.model.RealEstateInfosDomain
import kotlinx.coroutines.flow.Flow

fun interface GetRealEstatesListUseCase {
    suspend operator fun invoke(): Flow<List<RealEstateInfosDomain>>
}