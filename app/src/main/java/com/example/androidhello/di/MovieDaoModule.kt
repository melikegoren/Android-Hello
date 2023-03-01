package com.example.androidhello.di

import android.content.Context
import androidx.room.Room
import com.example.androidhello.data.dao.MovieDao
import com.example.androidhello.data.dao.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieDaoModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movies"
        ).build()

    @Provides
    fun provideMovieDao(database: MovieDatabase): MovieDao = database.movieDao()
}