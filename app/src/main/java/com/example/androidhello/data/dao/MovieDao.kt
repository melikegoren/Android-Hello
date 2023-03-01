package com.example.androidhello.data.dao

import androidx.room.*
import com.example.androidhello.domain.model.MovieModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovie(movie: MovieModel)

    @Update
    suspend fun updateMovie(movie: MovieModel)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieModel

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieModel>>

    @Query("SELECT * FROM movies WHERE isFav = :isFav ")
    suspend fun getFavMovies(isFav: Boolean): List<MovieModel>

}