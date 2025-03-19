package com.kev.aviv.data.di

import com.kev.aviv.data.datasource.RealEstateDataSource
import com.kev.aviv.data.repository.RealEstateRepositoryImpl
import com.kev.aviv.domain.repository.RealEstateRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRealEstateRepository(realEstateDataSource: RealEstateDataSource): RealEstateRepository {
        return RealEstateRepositoryImpl(realEstateDataSource)
    }
}