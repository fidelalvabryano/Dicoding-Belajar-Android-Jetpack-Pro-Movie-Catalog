package com.fidel.movietv.ui.favorite.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.fidel.movietv.data.source.local.entity.MovieEntity
import com.fidel.movietv.data.FilmRepository

class FavoriteMovieViewModel(private val filmRepository: FilmRepository): ViewModel() {
    fun getFavoriteMovies() : LiveData<PagedList<MovieEntity>> = filmRepository.getFavoriteMovies()

    fun setFavoriteMovie(movieEntity: MovieEntity) {
        val newState = !movieEntity.isFavorite
        filmRepository.setMovieFavorite(movieEntity, newState)
    }
}