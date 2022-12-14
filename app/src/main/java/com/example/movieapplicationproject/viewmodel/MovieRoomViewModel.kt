package com.example.movieapplicationproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapplicationproject.model.MovieRepository
import com.example.movieapplicationproject.model.local.data.MovieDatabaseEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieRoomViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    val readAllData : LiveData<List<MovieDatabaseEntity>> = repository.readAllMovieData

    fun addMovie(entity: MovieDatabaseEntity){
        viewModelScope.launch {
            repository.insertMovieData(entity)
        }
    }

    fun deleteMovie(entity: MovieDatabaseEntity){
        viewModelScope.launch {
            repository.deleteMovieData(entity)
        }
    }


}