package com.bhavanawagh.fetchrewardsapp.di

import com.bhavanawagh.fetchrewardsapp.api.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl(): String = "https://fetch-hiring.s3.amazonaws.com/"

    @Provides
    fun getGsonFactoryConvertor(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun getRetrofitInstance(baseUrl: String, gsonConverterFactory: GsonConverterFactory)
            : NetworkService {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(gsonConverterFactory)
            .build().create(NetworkService::class.java)
    }


}