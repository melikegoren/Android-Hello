package com.example.androidhello.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidhello.R
import com.example.androidhello.databinding.FragmentMoviesBinding
import com.example.androidhello.domain.mapper.MovieListMapper
import com.example.androidhello.domain.model.MovieModel
import com.example.androidhello.common.SharedPrefManager
import com.example.androidhello.ui.adapters.MoviesAdapter
import com.example.androidhello.ui.mapper.MovieListUiMapperImpl
import com.example.androidhello.ui.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment(), ClickHandleMoviesFragment {

    private var _binding:FragmentMoviesBinding? = null
    val binding get() = _binding!!

    private lateinit var movieAdapter: MoviesAdapter

    private val viewModel: MovieViewModel by viewModels()

    private lateinit var allMovies: List<MovieUiData>



    private val movieListMapper: MovieListMapper<MovieModel, MovieUiData> get() = MovieListUiMapperImpl()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToAddMovie()
        getAllMoviesFromDatabase()
        bindViewModel()
        getSharedPref()
        searchView()

    }

    private fun bindViewModel(){
        viewModel.movieListFromDatabase.observe(viewLifecycleOwner){
            allMovies = movieListMapper.map(it)
            val layoutManager = LinearLayoutManager(this.requireContext())
            binding.rwMovies.layoutManager = layoutManager
            movieAdapter = MoviesAdapter(allMovies as ArrayList<MovieUiData>, requireActivity(), this)
            binding.rwMovies.adapter = movieAdapter

        }
    }


    private fun getAllMoviesFromDatabase(){
        viewModel.getAllMovies()
    }

    private fun navigateToAddMovie(){
        binding.addMovie.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToAddMovieFragment()
            findNavController().navigate(action)
        }
    }

     private fun getSharedPref(){
        lifecycleScope.launch{
            val sharedPref = SharedPrefManager(requireContext())
            val id = sharedPref.getSharedPreference("movieId","0").toInt()
            val movie = viewModel.getMovieById(id)


            if(!viewModel.isIdValid(id)){
                binding.textView.text = getString(R.string.last_viewed)
            }
            else{
                binding.textView.text = getString(R.string.last_viewed)+movie.name.uppercase()
            }
        }
     }

    private fun searchView(){
        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                movieAdapter.getFilter().filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                movieAdapter.getFilter().filter(newText)
                return true
            }
        })
    }

    override fun onCardViewClick(id: Int) {
        val sharedPref = SharedPrefManager(requireContext())
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(id)
        findNavController().navigate(action)

    }

    override fun onAddToFavButtonClick(id: Int) {
        lifecycleScope.launch {
            val movie = viewModel.getMovieById(id)
            if(movie.isFav == true){
                Toast.makeText(this@MoviesFragment.context, R.string.already_added, Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, R.string.movie_added_fav, Toast.LENGTH_SHORT).show()
                viewModel.updateMovie(movie.copy(isFav = true))
            }
        }


    }

    override fun deleteMovieClick(id: Int) {
        lifecycleScope.launch {
            val movie = viewModel.getMovieById(id)
            viewModel.deleteMovie(movie)
            Toast.makeText(context, R.string.movie_deleted, Toast.LENGTH_SHORT).show()
        }
    }


}

interface ClickHandleMoviesFragment{
    fun onCardViewClick(id: Int)
    fun onAddToFavButtonClick(id: Int)
    fun deleteMovieClick(id: Int)

}