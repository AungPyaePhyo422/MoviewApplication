package com.example.movieapplicationproject.di

import com.example.movieapplicationproject.model.local.MovieDatabase
import com.example.movieapplicationproject.model.local.roomdb.MovieDao
import com.example.movieapplicationproject.model.remote.apicall.MovieApiCall
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideNetworkCall(retrofit: Retrofit) : MovieApiCall{
        return retrofit.create(MovieApiCall::class.java)
    }

    @Provides
    fun provideLocalData(database: MovieDatabase) : MovieDao{
        return database.movieDao()
    }

}