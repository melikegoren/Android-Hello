package com.example.androidhello.ui.addmovie

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.androidhello.ui.viewModel.MovieViewModel
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
        toolbarBackButton()

    }


    private fun setMovieTypesAdapter(){
        val types = resources.getStringArray(R.array.Types)
        val arrayAdapter = ArrayAdapter<String>(requireContext(), R.layout.dropdown_item, types)
        binding.autoComplete.setAdapter(arrayAdapter)
    }

    private fun createDatePickerDialog(){

        binding.date.setOnClickListener {
            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(), {
                    view, year, month, dayOfMonth  ->

                    val format = (dayOfMonth.toString()+"/"+(month+1)+"/"+year)
                    binding.date.setText(format) },
                day,year,month
            )

            datePickerDialog.updateDate(year,month,day)
            datePickerDialog.show()

        }


    }

    private fun saveButton(){
            textWatcher()
            binding.save.setOnClickListener{
                lifecycleScope.launch {
                    val name = binding.movieName.text.toString()
                    val genre = binding.autoComplete.text.toString()
                    val date = binding.date.text.toString()
                    val rate = binding.ratingBar.rating.toString()

                    val checkedId = binding.radioGroup.checkedRadioButtonId
                    if(checkedId == R.id.rewatch){
                        val whichTurn = R.string.rewatch.toString()
                        movie = MovieModel(0, name, genre, date, whichTurn, rate, false)
                        viewModel.addMovieToDatabase(movie)
                        Toast.makeText(requireContext(), R.string.movie_added_database, Toast.LENGTH_SHORT).show()
                        navigateToMoviesScreen()
                    }
                    else{
                        val whichTurn = R.string.first_time.toString()
                        movie = MovieModel(0, name, genre, date, whichTurn, rate, false)
                        viewModel.addMovieToDatabase(movie)
                        Toast.makeText(requireContext(), "Movie added.", Toast.LENGTH_SHORT).show()
                        navigateToMoviesScreen()
                    }
                }
            }
    }

    private fun textWatcher(){

        val textWatcher = object : TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.save.isEnabled = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val movieName = binding.movieName.text.toString()
                val date = binding.date.text.toString()
                val genre = binding.autoComplete.text
                binding.save.isEnabled = movieName.isNotEmpty() && date.isNotEmpty() && genre.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        binding.movieName.addTextChangedListener(textWatcher)
        binding.date.addTextChangedListener(textWatcher)
        binding.autoComplete.addTextChangedListener(textWatcher)
    }

    private fun toolbarBackButton(){
        binding.toolbar.setNavigationOnClickListener {
            navigateToMoviesScreen()
        }
    }


    fun navigateToMoviesScreen(){
        val action = AddMovieFragmentDirections.actionAddMovieFragmentToMoviesFragment()
        findNavController().navigate(action)
    }

}