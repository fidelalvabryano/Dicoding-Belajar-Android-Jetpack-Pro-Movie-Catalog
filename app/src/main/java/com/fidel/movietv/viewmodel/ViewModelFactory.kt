package com.fidel.movietv.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fidel.movietv.data.FilmRepository
import com.fidel.movietv.di.Injection
import com.fidel.movietv.ui.detail.DetailFilmViewModel
import com.fidel.movietv.ui.favorite.movie.FavoriteMovieViewModel
import com.fidel.movietv.ui.favorite.tvshow.FavoriteTvshowViewModel
import com.fidel.movietv.ui.movies.MoviesViewModel
import com.fidel.movietv.ui.tvshows.TvshowsViewModel

class ViewModelFactory private constructor(private val mFilmRepository: FilmRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                return MoviesViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(TvshowsViewModel::class.java) -> {
                return TvshowsViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                return FavoriteMovieViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteTvshowViewModel::class.java) -> {
                return FavoriteTvshowViewModel(mFilmRepository) as T
            }
            modelClass.isAssignableFrom(DetailFilmViewModel::class.java) -> {
                return DetailFilmViewModel(mFilmRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

}