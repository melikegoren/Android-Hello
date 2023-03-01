package com.example.androidhello.ui.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidhello.databinding.MovieListItemBinding
import com.example.androidhello.ui.movies.MovieUiData
import com.example.androidhello.ui.movies.Navigation
import java.util.*
import kotlin.collections.ArrayList

class MoviesAdapter(private val movieList: ArrayList<MovieUiData>, private val context: Activity,
                    private val navigation: Navigation
): RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {



    private val initialMovieList = kotlin.collections.ArrayList<MovieUiData>().apply {
        movieList.let {
            addAll(it)
       }
    }

    class MovieViewHolder(val binding: MovieListItemBinding): RecyclerView.ViewHolder(binding.root) {

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]

        holder.binding.apply {
            movieName.text = movie.movieName

            cwMovie.setOnClickListener {
                navigation.onCardViewClick(movie.id)

            }

        }



    }

    override fun getItemCount(): Int = movieList.size



   private fun setFilter() = object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filteredList: ArrayList<MovieUiData> = ArrayList()
                if(p0.isNullOrEmpty()){
                    initialMovieList.let { filteredList.addAll(it) }
                }
                else{
                    val query = p0.toString().trim().lowercase()
                    initialMovieList.forEach{
                        if(it.movieName.toLowerCase(Locale.ROOT).contains(query)){
                            filteredList.add(it)
                        }
                    }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                if(p1?.values is ArrayList<*>){
                    movieList.clear()
                    movieList.addAll(p1.values as ArrayList<MovieUiData>)
                    notifyDataSetChanged()
                }
            }

        }

    private fun getFilter(): Filter {
       return setFilter()
    }

}