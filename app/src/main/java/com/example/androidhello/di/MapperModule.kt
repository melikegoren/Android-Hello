package com.example.androidhello.di

import com.example.androidhello.ui.moviedetail.MovieDetailUiMapperImpl
import com.example.androidhello.domain.mapper.MovieListMapper
import com.example.androidhello.ui.movies.MovieListUiMapperImpl
import com.example.androidhello.domain.mapper.MovieMapper
import com.example.androidhello.domain.model.MovieModel
import com.example.androidhello.ui.moviedetail.MovieDetailUiData
import com.example.androidhello.ui.movies.MovieUiData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MapperModule {

    @Binds
    @ViewModelScoped
    abstract fun bindMovieListUiMapper(movieListUiMapperImpl: MovieListUiMapperImpl): MovieListMapper<MovieModel, MovieUiData>

    @Binds
    @ViewModelScoped
    abstract fun bindMovieDetailUiMapper(movieDetailUiMapperImpl: MovieDetailUiMapperImpl): MovieMapper<MovieModel, MovieDetailUiData>
}