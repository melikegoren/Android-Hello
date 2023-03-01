package com.example.androidhello.data.dao


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidhello.domain.model.MovieModel

@Database(entities = [MovieModel::class], version = 1, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao
}