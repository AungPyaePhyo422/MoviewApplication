package com.example.movieapplicationproject.model

import androidx.lifecycle.LiveData
import com.example.movieapplicationproject.helper.Resource
import com.example.movieapplicationproject.model.local.MovieLocalDatasource
import com.example.movieapplicationproject.model.local.data.MovieDatabaseEntity
import com.example.movieapplicationproject.model.remote.MovieDataSource
import com.example.movieapplicationproject.model.remote.data.MovieNetworkData
import com.example.movieapplicationproject.model.remote.data.PopularMovieResponse
import com.example.movieapplicationproject.model.remote.data.UpcomingMovieResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val networkDataSource: MovieDataSource,
    private val localDatasource: MovieLocalDatasource
) {

    suspend fun getPopularMovie() : Flow<Resource<PopularMovieResponse>>{
        return networkDataSource.getPopularMovieFlow()
    }

    suspend fun getUpcomingMovie() : Flow<Resource<UpcomingMovieResponse>>{
        return networkDataSource.getUpcomingMovieFlow()
    }

    suspend fun getMovieDetail(movieId : Int) : Flow<Resource<MovieNetworkData>>{
        return networkDataSource.getMovieDetailFlow(movieId)
    }

    suspend fun deleteMovieData(movieDatabaseEntity: MovieDatabaseEntity){
        return localDatasource.deleteMovieData(movieDatabaseEntity)
    }

    suspend fun insertMovieData(movieDatabaseEntity: MovieDatabaseEntity){
        return localDatasource.insertMovieData(movieDatabaseEntity)
    }

    val readAllMovieData : LiveData<List<MovieDatabaseEntity>> = localDatasource.readAllMovieData


}