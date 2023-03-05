package com.example.androidhello.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidhello.databinding.FavoriteMovieItemBinding
import com.example.androidhello.ui.favorites.ClickHandleFavoritesFragment
import com.example.androidhello.ui.movies.MovieUiData

class FavMovieAdapter(private val favMovieList: List<MovieUiData>, private val context: Context, private val click: ClickHandleFavoritesFragment)
    :RecyclerView.Adapter<FavMovieAdapter.FavMovieViewHolder>() {


    class FavMovieViewHolder(val binding: FavoriteMovieItemBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavMovieViewHolder {
        val binding = FavoriteMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavMovieViewHolder(binding)

    }

    override fun onBindViewHolder(holder: FavMovieViewHolder, position: Int) {
        val favMovie = favMovieList[position]
        Log.d("aydi", favMovie.id.toString())

        holder.binding.movieName.text = favMovie.movieName
        holder.binding.removeFromFav.setOnClickListener {
            click.removeFromFav(favMovie.id)
        }
    }

    override fun getItemCount(): Int = favMovieList.size
}