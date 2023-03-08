package com.example.androidhello.data.repository


import com.example.androidhello.data.source.LocalDataSource
import com.example.androidhello.domain.model.MovieModel
import com.example.androidhello.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor (
    private val localDataSource: LocalDataSource): MovieRepository {
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

    override suspend fun deleteMovie(movie: MovieModel) {
        withContext(Dispatchers.IO){
            localDataSource.deleteMovie(movie)
        }
    }

    override suspend fun isIdValid(id: Int): Boolean {
        return withContext(Dispatchers.IO){
            localDataSource.isIdValid(id)
        }
    }

    override suspend fun getMovieById(id: Int): MovieModel {
        return withContext(Dispatchers.IO){
            localDataSource.getMovieById(id)
        }
    }

    override fun getAllMovies(): Flow<List<MovieModel>> = localDataSource.getAllMovies()

    override fun getFavMovies(): Flow<List<MovieModel>> = localDataSource.getFavMovies()


}