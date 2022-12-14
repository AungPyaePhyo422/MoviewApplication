package com.example.movieapplicationproject.model.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_db")
data class MovieDatabaseEntity(

    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val movieId : Int,
    val movieTitle : String,
    val movieOverView : String,
    val posterPath : String,
    val backPath : String,
    val rating : String
) : java.io.Serializable
