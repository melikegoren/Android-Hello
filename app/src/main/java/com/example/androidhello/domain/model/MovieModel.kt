package com.example.androidhello.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val genre: String,
    val date: String,
    val whichTurn: String,
    val rate: String,
    val isFav: Boolean = false,
)