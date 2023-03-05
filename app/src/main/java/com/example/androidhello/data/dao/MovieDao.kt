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

    @Delete
    suspend fun deleteMovie(movie: MovieModel)

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovieById(id: Int): MovieModel

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieModel>>

    @Query("SELECT * FROM movies WHERE isFav = 1 ")
    fun getFavMovies(): Flow<List<MovieModel>>

    @Query("SELECT (SELECT COUNT(*) FROM movies) = 0")
    suspend fun isDbEmpty(): Boolean

    @Query("SELECT EXISTS(SELECT * FROM movies WHERE id = :id)")
    suspend fun isIdValid(id: Int): Boolean

}