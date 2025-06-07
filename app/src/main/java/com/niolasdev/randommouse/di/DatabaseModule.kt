package com.niolasdev.randommouse.di

import android.content.Context
import com.niolasdev.storage.CatsDatabase
import com.niolasdev.storage.catsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CatsDatabase {
        return catsDatabase(context)
    }
}