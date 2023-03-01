package com.example.androidhello.ui.movies

import com.example.androidhello.domain.mapper.MovieListMapper
import com.example.androidhello.domain.model.MovieModel
import javax.inject.Inject

class MovieListUiMapperImpl @Inject constructor(): MovieListMapper<MovieModel, MovieUiData> {
    override fun map(input: List<MovieModel>): List<MovieUiData> {
        return input.map {
            MovieUiData(
                id = it.id,
                movieName = it.name
            )

        }
    }
}