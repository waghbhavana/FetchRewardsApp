package com.bhavanawagh.fetchrewardsapp.di

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule  {

    @Provides
    fun provideBaseUrl(): String = "https://fetch-hiring.s3.amazonaws.com/"
}