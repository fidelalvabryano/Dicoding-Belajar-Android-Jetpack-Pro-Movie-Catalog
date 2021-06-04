package com.fidel.movietv.ui.movies

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fidel.movietv.data.source.local.entity.MovieEntity
import com.fidel.movietv.databinding.ItemsMovieBinding
import com.fidel.movietv.ui.detail.DetailFilmActivity
import com.fidel.movietv.vo.Resource

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.FilmViewHolder>() {
    private val listFilms = ArrayList<MovieEntity>()

    fun setFilms(films: List<MovieEntity>?) {
        if (films == null) return
        this.listFilms.clear()
        this.listFilms.addAll(films)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.FilmViewHolder {
        val itemsMovieBinding = ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MoviesAdapter.FilmViewHolder, position: Int) {
        val film = listFilms[position]
        holder.bind(film)
    }

    override fun getItemCount(): Int = listFilms.size

    class FilmViewHolder(private val binding: ItemsMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                titletext.text = movie.title
                yeartext.text = movie.year
                Glide.with(itemView.context)
                        .load(movie.image)
                        .into(movieimage)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFilmActivity::class.java)
                    intent.putExtra(DetailFilmActivity.EXTRA_FILM, movie.filmId)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }

}