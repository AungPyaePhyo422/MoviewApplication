package com.example.movieapplicationproject.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieapplicationproject.model.local.MovieDatabase
import com.example.movieapplicationproject.model.local.data.MovieDatabaseEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) : MovieDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

}