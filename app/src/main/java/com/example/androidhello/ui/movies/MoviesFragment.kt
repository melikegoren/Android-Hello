package com.example.androidhello.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidhello.R
import com.example.androidhello.databinding.FragmentMoviesBinding
import com.example.androidhello.domain.mapper.MovieListMapper
import com.example.androidhello.domain.model.MovieModel
import com.example.androidhello.ui.SharedPrefManager
import com.example.androidhello.ui.adapters.MoviesAdapter
import com.example.androidhello.ui.moviedetail.MovieDetailFragment
import com.example.androidhello.ui.viewModels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment(), Navigation {

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

     fun getSharedPref(){
        lifecycleScope.launch{
            val sharedPref = SharedPrefManager(requireContext())
            val id = sharedPref.getSharedPreference("movieId","0").toInt()
            if(id == 0){
                binding.textView.text = getString(R.string.last_viewed)
            }
            else{
                val movie = viewModel.getMovieById(id)
                binding.textView.text = getString(R.string.last_viewed)+movie.name
            }

        }





    }

    override fun onCardViewClick(id: Int) {
        val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(id)
        findNavController().navigate(action)
    }





}

interface Navigation{
    fun onCardViewClick(id: Int)
}