package com.example.androidhello.ui.favorites


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidhello.R
import com.example.androidhello.databinding.FragmentFavoritesBinding
import com.example.androidhello.domain.mapper.MovieListMapper
import com.example.androidhello.domain.model.MovieModel
import com.example.androidhello.ui.adapters.FavMovieAdapter
import com.example.androidhello.ui.mapper.MovieListUiMapperImpl
import com.example.androidhello.ui.movies.MovieUiData
import com.example.androidhello.ui.viewModel.MovieViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FavoritesFragment : Fragment(), ClickHandleFavoritesFragment{

    private var _binding: FragmentFavoritesBinding? = null
    val binding: FragmentFavoritesBinding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    private lateinit var favMovieAdapter: FavMovieAdapter

    private lateinit var allFavMovies: List<MovieUiData>

    private val favMoviesUiMapper: MovieListMapper<MovieModel, MovieUiData> get() = MovieListUiMapperImpl()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        getMovies()
    }

    private fun getMovies(){
        viewModel.getFavMovies()
    }

    private fun setAdapter(){
        viewModel.favMovieList.observe(viewLifecycleOwner){
            allFavMovies = favMoviesUiMapper.map(it)
            val layoutManager = LinearLayoutManager(this.requireContext())
            binding.rwFavorites.layoutManager = layoutManager
            favMovieAdapter = FavMovieAdapter(allFavMovies,requireContext(),this)
            binding.rwFavorites.adapter = favMovieAdapter
        }
    }

    override fun removeFromFav(id: Int) {
        lifecycleScope.launch {
            val movie = viewModel.getMovieById(id)
            viewModel.updateMovie(movie.copy(isFav = false))
            Toast.makeText(context, R.string.movie_removed, Toast.LENGTH_SHORT).show()
        }
    }
}

interface ClickHandleFavoritesFragment{
    fun removeFromFav(id: Int)
}
