package com.example.androidhello.domain.mapper

interface MovieListMapper<I, O> : MovieMapper<List<I>, List<O>>
interface MovieDetailMapper<I,O>: MovieMapper<I,O>

interface MovieMapper<I, O> {
    fun map(input: I): O
}