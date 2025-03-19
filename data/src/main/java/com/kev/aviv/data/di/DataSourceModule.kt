package com.kev.aviv.data.di

import com.kev.aviv.data.api.RealEstateApi
import com.kev.aviv.data.datasource.RealEstateDataSource
import com.kev.aviv.data.datasource.impl.RealEstateDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideRealEstateDataSource(realEstateApi: RealEstateApi): RealEstateDataSource {
        return RealEstateDataSourceImpl(realEstateApi)
    }
}