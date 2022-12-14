package com.example.movieapplicationproject.model.local.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapplicationproject.model.local.data.MovieDatabaseEntity
import retrofit2.http.DELETE

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(movieDatabaseEntity: MovieDatabaseEntity)

    @Query(" SELECT * FROM movie_db ORDER BY id ASC")
    fun readAllData(): LiveData<List<MovieDatabaseEntity>>

//    @Query(" SELECT * FROM movie_db WHERE movieId LIKE :favId")
//    fun readFavData(favId : Int): LiveData<MovieDatabaseEntity>

    @Delete
    suspend fun deleteData(movieDatabaseEntity: MovieDatabaseEntity)
}