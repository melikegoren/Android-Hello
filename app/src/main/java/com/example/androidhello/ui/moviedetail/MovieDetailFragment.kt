package com.example.androidhello.ui.moviedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.androidhello.R
import com.example.androidhello.databinding.FragmentMovieDetailBinding
import com.example.androidhello.domain.mapper.MovieDetailMapper
import com.example.androidhello.domain.model.MovieModel
import com.example.androidhello.common.SharedPrefManager
import com.example.androidhello.common.CryptoManager
import com.example.androidhello.ui.mapper.MovieDetailUiMapperImpl
import com.example.androidhello.ui.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    val binding: FragmentMovieDetailBinding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    private val args: MovieDetailFragmentArgs by navArgs()

    private val cryptoManager = CryptoManager()

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
        encryptButton()
        decryptButton()
        toolbarBackButton()

    }

    private fun getMovieByIdd(){
        lifecycleScope.launch{
            val id = args.id
            val movie = viewModel.getMovieById(id)
            val mappedMovie =  movieDetailMapper.map(movie)
            val whichRunn = mappedMovie.whichTurn
            binding.apply {
                movieName.text = mappedMovie.name
                genre.text = getString(R.string.Genre)+mappedMovie.genre
                date.text = getString(R.string.Date)+mappedMovie.date
                rate.text = mappedMovie.rate
                whichRun.text = mappedMovie.whichTurn
            }
        }

    }

    private fun encryptButton(){
        binding.encrypt.setOnClickListener {
            encryptRate(binding.rate.text.toString())
            binding.rate.text = ""
            binding.encrypt.isEnabled = false

        }
    }

    private fun decryptButton(){
        binding.decrypt.setOnClickListener {
            binding.decrypt.isEnabled = true
            decryptRate()
        }
    }

    private fun setSharedPref(){
        val sharedPref = SharedPrefManager(requireContext())
        sharedPref.setSharedPreference("movieId", args.id.toString())
    }

    private fun encryptRate(text: String){
        val bytes = text.encodeToByteArray()
        val file = File(requireActivity().filesDir,"rate.txt")
        if(!file.exists()){
            file.createNewFile()
        }
        val fos = FileOutputStream(file)
        val textToDecrypt = cryptoManager.encrypt(bytes, fos).decodeToString()

    }

    private fun decryptRate(){
        val file = File(requireActivity().filesDir,"rate.txt")
        val text = cryptoManager.decrypt(
            inputStream = FileInputStream(file)
        ).decodeToString()
        binding.rate.text = text
        //binding.invisibleText.text = text

    }

    private fun toolbarBackButton(){
        binding.toolbar.setNavigationOnClickListener {
            val action = MovieDetailFragmentDirections.actionMovieDetailFragmentToMoviesFragment()
            findNavController().navigate(action)
        }

    }
}