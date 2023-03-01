package com.example.androidhello.data.source

import com.example.androidhello.data.dao.MovieDao
import com.example.androidhello.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
): LocalDataSource {
    override suspend fun addMovie(movie: MovieModel) {
        movieDao.addMovie(movie)
    }

    override suspend fun updateMovie(movie: MovieModel) {
        movieDao.updateMovie(movie)
    }

    override suspend fun getMovieById(id: Int): MovieModel =
        movieDao.getMovieById(id)


    override fun getAllMovies(): Flow<List<MovieModel>> =
        movieDao.getAllMovies()

}