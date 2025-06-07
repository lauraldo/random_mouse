package com.niolasdev.randommouse.di

import com.niolasdev.network.CatApiService
import com.niolasdev.randommouse.domain.CatRepository
import com.niolasdev.randommouse.domain.ICatRepository
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
    fun provideCatRepository(
        apiService: CatApiService,
        database: CatsDatabase,
    ): ICatRepository {
        return CatRepository(apiService, database)
    }
}