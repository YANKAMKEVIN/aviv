package com.kev.aviv.domain

import com.kev.aviv.domain.repository.RealEstateRepository
import com.kev.aviv.domain.usecase.GetRealEstatesListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Singleton
    @Provides
    fun provideGetRealEstatesListUseCase(realEstateRepository: RealEstateRepository): GetRealEstatesListUseCase =
        GetRealEstatesListUseCase(realEstateRepository::getRealEstateListings)
}