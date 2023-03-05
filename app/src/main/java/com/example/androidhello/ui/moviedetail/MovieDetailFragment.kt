package com.example.androidhello.ui.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.androidhello.R
import com.example.androidhello.databinding.FragmentMovieDetailBinding
import com.example.androidhello.domain.mapper.MovieDetailMapper
import com.example.androidhello.domain.model.MovieModel
import com.example.androidhello.ui.SharedPrefManager
import com.example.androidhello.ui.mapper.MovieDetailUiMapperImpl
import com.example.androidhello.ui.viewModels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    val binding: FragmentMovieDetailBinding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    private val args: MovieDetailFragmentArgs by navArgs()

    private val movieDetailMapper: MovieDetailMapper<MovieModel, MovieDetailUiData> get() =  MovieDetailUiMapperImpl()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMovieByIdd()
        setSharedPref()




    }

    fun getMovieByIdd(){
        lifecycleScope.launch{
            val id = args.id
            val movie = viewModel.getMovieById(id)
            val mappedMovie =  movieDetailMapper.map(movie)
            binding.apply {
                movieName.text = mappedMovie.name
                genre.text = getString(R.string.Genre)+mappedMovie.genre
                date.text = getString(R.string.Date)+mappedMovie.date
                whichRun.text = mappedMovie.whichTurn
                rate.text = mappedMovie.rate

            }
        }


    }

    fun setSharedPref(){
        val sharedPref = SharedPrefManager(requireContext())
        sharedPref.setSharedPreference("movieId", args.id.toString())
    }








}