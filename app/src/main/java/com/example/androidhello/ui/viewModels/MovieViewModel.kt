package com.example.androidhello.ui.viewModels

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.example.androidhello.domain.model.MovieModel
import com.example.androidhello.domain.repository.MovieRepository
import com.example.androidhello.ui.movies.MovieUiData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel(){



    private val _movieListFromDatabase = MutableLiveData<List<MovieModel>>()
    val movieListFromDatabase: LiveData<List<MovieModel>> = _movieListFromDatabase

    private val _favMovieList = MutableLiveData<List<MovieModel>>()
    val favMovieList: LiveData<List<MovieModel>> get() = _favMovieList

    private val _movieUiState = MutableLiveData<HomeUiState>()
    val movieUiState: LiveData<HomeUiState> get() = _movieUiState

    fun getAllMovies(){
        viewModelScope.launch {
            movieRepository.getAllMovies().onStart {  }
                .onCompletion {  }
                .collect{
                    _movieListFromDatabase.postValue(it)
                }
        }

    }

    fun getFavMovies(){
        viewModelScope.launch {
            movieRepository.getFavMovies().onStart {  }
                .onCompletion {  }
                .collect{
                    _favMovieList.postValue(it)
                }
        }
    }

    fun addMovieToDatabase(movie: MovieModel){
        viewModelScope.launch {
            movieRepository.addMovie(movie)
        }
    }

    fun updateMovie(movie: MovieModel){
        viewModelScope.launch {
            movieRepository.updateMovie(movie)
        }
    }

    fun deleteMovie(movie: MovieModel){
        viewModelScope.launch {
            movieRepository.deleteMovie(movie)
        }
    }

    suspend fun isDbEmpty(): Boolean{
        return withContext(Dispatchers.IO){
            movieRepository.isDbEmpty()
        }
    }

    suspend fun isIdValid(id: Int): Boolean{
        return withContext(Dispatchers.IO){
            movieRepository.isIdValid(id)
        }
    }

    suspend fun getMovieById(id: Int): MovieModel{
        return withContext(Dispatchers.IO){
            movieRepository.getMovieById(id)
        }

    }






}

sealed class HomeUiState() {
    object Loading : HomeUiState()
    data class Success(val data: List<MovieUiData>) : HomeUiState()
    data class Error(@StringRes val message: Int) : HomeUiState()
}