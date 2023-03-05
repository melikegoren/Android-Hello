package com.example.androidhello.ui.mapper

import com.example.androidhello.domain.mapper.MovieDetailMapper
import com.example.androidhello.domain.mapper.MovieMapper
import com.example.androidhello.domain.model.MovieModel
import com.example.androidhello.ui.moviedetail.MovieDetailUiData
import javax.inject.Inject

class MovieDetailUiMapperImpl @Inject constructor(): MovieMapper<MovieModel, MovieDetailUiData>,
    MovieDetailMapper<MovieModel, MovieDetailUiData> {
    override fun map(input: MovieModel): MovieDetailUiData {
        return input.let {
            MovieDetailUiData(
                id = it.id,
                name = it.name,
                genre = it.genre,
                date = it.date,
                whichTurn = it.whichTurn,
                rate = it.rate
            )
        }
    }

}