package com.example.androidhello.domain.repository


import com.example.androidhello.data.source.LocalDataSource
import com.example.androidhello.domain.model.MovieModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor (
    private val localDataSource: LocalDataSource): MovieRepository{
    override suspend fun addMovie(movie: MovieModel) {
        withContext(Dispatchers.IO){
            localDataSource.addMovie(movie)
        }
    }

    override suspend fun updateMovie(movie: MovieModel) {
        withContext(Dispatchers.IO){
            localDataSource.updateMovie(movie)
        }
    }

    override suspend fun getMovieById(id: Int): MovieModel {
        return withContext(Dispatchers.IO){
            localDataSource.getMovieById(id)
        }
    }

    override fun getAllMovies(): Flow<List<MovieModel>> = localDataSource.getAllMovies()



}