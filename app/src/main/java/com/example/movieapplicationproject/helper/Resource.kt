package com.example.movieapplicationproject.helper

sealed class Resource<out T>{

    data class Success<out T>(val data : T) : Resource<T>()

    data class Loading<out T>(val isLoading : Boolean = true) : Resource<T>()

    data class Error<out T>(val errorMessage : String , val throwable: Throwable) : Resource<T>()

    data class Start<out T>(val start : String = "Started") : Resource<T>()
}
