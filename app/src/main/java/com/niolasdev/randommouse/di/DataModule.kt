package com.niolasdev.randommouse.di

import com.niolasdev.network.CatApiService
import com.niolasdev.randommouse.domain.CatRepository
import com.niolasdev.randommouse.domain.ICatRepository
import com.niolasdev.randommouse.domain.NetworkMonitor
import com.niolasdev.randommouse.domain.NetworkMonitorImpl
import com.niolasdev.storage.CatsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideNetworkMonitor(impl: NetworkMonitorImpl): NetworkMonitor = impl

    @Provides
    @Singleton
    fun provideCatRepository(
        apiService: CatApiService,
        database: CatsDatabase,
        networkMonitor: NetworkMonitor,
    ): ICatRepository {
        return CatRepository(apiService, database, networkMonitor)
    }
}