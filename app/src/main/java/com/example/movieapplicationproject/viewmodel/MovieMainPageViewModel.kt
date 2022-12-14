package com.example.movieapplicationproject.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplicationproject.helper.AsyncResourceLiveData
import com.example.movieapplicationproject.helper.AsyncViewResource
import com.example.movieapplicationproject.helper.Resource
import com.example.movieapplicationproject.model.MovieRepository
import com.example.movieapplicationproject.model.remote.data.MovieNetworkData
import com.example.movieapplicationproject.model.remote.data.PopularMovieResponse
import com.example.movieapplicationproject.model.remote.data.UpcomingMovieResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieMainPageViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel(){

    val popularMovie = AsyncResourceLiveData<PopularMovieResponse?>()
    val upcomingMovie = AsyncResourceLiveData<UpcomingMovieResponse?>()
    val movieDetail = AsyncResourceLiveData<MovieNetworkData>()

    fun getUpcomingMovie(){
        viewModelScope.launch(Dispatchers.IO) {
           repository.getUpcomingMovie().collect{
               when(it){
                   is Resource.Start -> {
                       Log.d("ResourceTest", it.start.toString())
                   }
                   is Resource.Success -> {
                       upcomingMovie.postSuccess(it.data)
                   }
                   is Resource.Error -> {
                       upcomingMovie.postFail(it.throwable, it.errorMessage)
                   }
                   is Resource.Loading -> {
                       upcomingMovie.postLoading()
                   }
               }
           }
        }
    }

    fun getPopularMovie(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPopularMovie().collect{
                when(it){
                    is Resource.Start -> {
                        Log.d("ResourceTest", it.start.toString())
                    }
                    is Resource.Success -> {
                        popularMovie.postSuccess(it.data)
                    }
                    is Resource.Error -> {
                        popularMovie.postFail(it.throwable, it.errorMessage)
                    }
                    is Resource.Loading -> {
                        popularMovie.postLoading()
                    }
                }
            }
        }
    }

    fun getMovieDetail(movieId : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMovieDetail(movieId).collect{
                when(it){
                    is Resource.Start -> {
                        Log.d("ResourceTest", it.start.toString())
                    }
                    is Resource.Success -> {
                        movieDetail.postSuccess(it.data)
                    }
                    is Resource.Error -> {
                        movieDetail.postFail(it.throwable, it.errorMessage)
                    }
                    is Resource.Loading -> {
                        movieDetail.postLoading()
                    }
                }
            }
        }
    }
}