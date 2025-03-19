package com.kev.aviv.data.di

import com.kev.aviv.data.api.RealEstateApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideRealEstateApi(): RealEstateApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_REAL_ESTATE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RealEstateApi::class.java)
    }

    companion object {
        private const val BASE_URL_REAL_ESTATE = "https://gsl-apps-technical-test.dignp.com/"
    }
}