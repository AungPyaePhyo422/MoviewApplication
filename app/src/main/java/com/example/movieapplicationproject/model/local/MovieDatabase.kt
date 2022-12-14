package com.example.movieapplicationproject.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movieapplicationproject.model.local.data.MovieDatabaseEntity
import com.example.movieapplicationproject.model.local.roomdb.MovieDao

@Database(entities = [MovieDatabaseEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDao

}