package com.example.movieapplicationproject.model.local

import androidx.lifecycle.LiveData
import com.example.movieapplicationproject.helper.Resource
import com.example.movieapplicationproject.model.local.data.MovieDatabaseEntity
import com.example.movieapplicationproject.model.local.roomdb.MovieDao
import com.example.movieapplicationproject.model.remote.data.MovieNetworkData
import com.example.movieapplicationproject.model.remote.data.PopularMovieResponse
import com.example.movieapplicationproject.model.remote.data.UpcomingMovieResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDatasource @Inject constructor(
    private val movieDao: MovieDao
){

    suspend fun deleteMovieData(movieDatabaseEntity: MovieDatabaseEntity) = movieDao.deleteData(movieDatabaseEntity)

    suspend fun insertMovieData(movieDatabaseEntity: MovieDatabaseEntity) = movieDao.insertData(movieDatabaseEntity)

    val readAllMovieData : LiveData<List<MovieDatabaseEntity>> = movieDao.readAllData()


}
