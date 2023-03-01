package com.example.androidhello.data.source

import com.example.androidhello.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun addMovie(movie: MovieModel)
    suspend fun updateMovie(movie: MovieModel)
    suspend fun getMovieById(id: Int): MovieModel
    fun getAllMovies(): Flow<List<MovieModel>>


}