package com.example.androidhello.ui.addmovie

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.androidhello.R
import com.example.androidhello.domain.model.MovieModel
import com.example.androidhello.databinding.FragmentAddMovieBinding
import com.example.androidhello.ui.viewModels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

import java.util.Calendar


@AndroidEntryPoint
class AddMovieFragment : Fragment() {

    private var _binding: FragmentAddMovieBinding? = null
    val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()

    private lateinit var movie: MovieModel





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createDatePickerDialog()
        setMovieTypesAdapter()
        saveButton()

    }

    override fun onResume() {
        super.onResume()
        setMovieTypesAdapter()
    }

    fun setMovieTypesAdapter(){
        val types = resources.getStringArray(R.array.Types)
        val arrayAdapter = ArrayAdapter<String>(requireContext(), R.layout.dropdown_item, types)
        binding.autoComplete.setAdapter(arrayAdapter)


    }

    fun createDatePickerDialog(){

        binding.date.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(), {
                    view, year, month, dayOfMonth  ->

                    val format = (dayOfMonth.toString()+"/"+month+"/"+year)
                    binding.date.setText(format) },
                day,year,month
            ).show()
         }


    }

    fun saveButton(){
        binding.apply {
            save.setOnClickListener{
                lifecycleScope.launch {
                    val name = movieName.text.toString()
                    val genre = autoComplete.text.toString()
                    val date = date.text.toString()
                    val rate = ratingBar.rating.toString()

                    val checkedId = radioGroup.checkedRadioButtonId
                    if(checkedId == R.id.rewatch){
                        val whichTurn = "rewatch"
                        movie = MovieModel(0, name, genre, date, whichTurn, rate, false)
                        viewModel.addMovieToDatabase(movie)
                        Toast.makeText(requireContext(), "Movie added.", Toast.LENGTH_SHORT).show()
                        navigateToMoviesScreen()

                    }
                    else{
                        val whichTurn = "first time"
                        movie = MovieModel(0, name, genre, date, whichTurn, rate, false)
                        viewModel.addMovieToDatabase(movie)
                        Toast.makeText(requireContext(), "Movie added.", Toast.LENGTH_SHORT).show()
                        navigateToMoviesScreen()
                    }
                }
            }
        }
    }

    fun navigateToMoviesScreen(){
        val action = AddMovieFragmentDirections.actionAddMovieFragmentToMoviesFragment()
        findNavController().navigate(action)
    }





}