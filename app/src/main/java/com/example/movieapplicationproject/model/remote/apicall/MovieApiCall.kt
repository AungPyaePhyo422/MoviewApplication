package com.example.movieapplicationproject.model.remote.apicall

import com.example.movieapplicationproject.model.remote.data.MovieNetworkData
import com.example.movieapplicationproject.model.remote.data.PopularMovieResponse
import com.example.movieapplicationproject.model.remote.data.UpcomingMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiCall {

    @GET("3/movie/popular?api_key=c32db68479a6422fd7e60e3b20fe4954&language=en-US&page=1")
    suspend fun getPopularMovieData() : Response<PopularMovieResponse>

    @GET("3/movie/upcoming?api_key=c32db68479a6422fd7e60e3b20fe4954&language=en-US&page=1")
    suspend fun getUpComingMovieData() : Response<UpcomingMovieResponse>

    @GET("3/movie/{movieId}?api_key=c32db68479a6422fd7e60e3b20fe4954&language=en-US")
    suspend fun getMovieDetail(
        @Path("movieId") movieId : Int
    ) : Response<MovieNetworkData>
}