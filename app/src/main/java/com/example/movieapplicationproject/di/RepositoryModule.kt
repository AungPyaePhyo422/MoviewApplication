package com.example.movieapplicationproject.di

import com.example.movieapplicationproject.model.MovieRepository
import com.example.movieapplicationproject.model.local.MovieDatabase
import com.example.movieapplicationproject.model.local.MovieLocalDatasource
import com.example.movieapplicationproject.model.remote.MovieDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideMovieRepository(networkDataSource : MovieDataSource, localDataSource: MovieLocalDatasource
    ) : MovieRepository{
        return MovieRepository(
            networkDataSource,
            localDataSource
        )
    }

}