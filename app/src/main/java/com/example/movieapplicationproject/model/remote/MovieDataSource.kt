package com.example.movieapplicationproject.model.remote

import com.example.movieapplicationproject.helper.Resource
import com.example.movieapplicationproject.model.remote.apicall.MovieApiCall
import com.example.movieapplicationproject.model.remote.data.MovieNetworkData
import com.example.movieapplicationproject.model.remote.data.PopularMovieResponse
import com.example.movieapplicationproject.model.remote.data.UpcomingMovieResponse
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    private val movieApiCall: MovieApiCall
) : BaseDataSource() {

    suspend fun getPopularMovieFlow() : kotlinx.coroutines.flow.Flow<Resource<PopularMovieResponse>> {
        return flow{
            emit(Resource.Start())
            emit(Resource.Loading())
            emit(getResultOrError { movieApiCall.getPopularMovieData() })
        }
    }

    suspend fun getUpcomingMovieFlow() : kotlinx.coroutines.flow.Flow<Resource<UpcomingMovieResponse>> {
        return flow{
            emit(Resource.Start())
            emit(Resource.Loading())
            emit(getResultOrError { movieApiCall.getUpComingMovieData() })
        }
    }

    suspend fun getMovieDetailFlow(movieId : Int) : kotlinx.coroutines.flow.Flow<Resource<MovieNetworkData>> {
        return flow{
            emit(Resource.Start())
            emit(Resource.Loading())
            emit(getResultOrError { movieApiCall.getMovieDetail(movieId) })
        }
    }



}