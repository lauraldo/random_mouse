package com.niolasdev.randommouse.di

import com.niolasdev.network.CatApiService
import com.niolasdev.network.NetworkProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val networkProvider = NetworkProvider()

    @Provides
    @Singleton
    fun provideCatApi(): CatApiService {
        return networkProvider.provideCatApi()
    }
}