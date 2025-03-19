package com.kev.aviv.domain.usecase

import com.kev.aviv.domain.model.RealEstateInfosDomain
import kotlinx.coroutines.flow.Flow

fun interface GetRealEstateDetailsUseCase {
    suspend operator fun invoke(id:String): Flow<RealEstateInfosDomain>
}