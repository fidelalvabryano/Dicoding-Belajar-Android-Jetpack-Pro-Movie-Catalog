package com.fidel.movietv.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.fidel.movietv.data.source.local.entity.MovieEntity
import com.fidel.movietv.data.source.local.entity.TVShowEntity
import com.fidel.movietv.data.FilmRepository
import com.fidel.movietv.vo.Resource

class DetailFilmViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    val movieId = MutableLiveData<String>()
    val tvShowId = MutableLiveData<String>()

    fun setSelectedMovie(movieId: String) {
        this.movieId.value = movieId
    }

    fun setSelectedTVShow(tvShowId: String) {
        this.tvShowId.value = tvShowId
    }

    var getDetailMovie : LiveData<Resource<MovieEntity>> = Transformations.switchMap(movieId) {
        id -> filmRepository.getMovieDetail(id)
    }

    var getDetailTVShow : LiveData<Resource<TVShowEntity>> = Transformations.switchMap(tvShowId) {
        id -> filmRepository.getTVShowDetail(id)
    }

    fun setFavoriteFilm() {
        val movieResource = getDetailMovie.value
        val tvShowResource = getDetailTVShow.value
        if (movieResource != null) {
            val movieDetail = movieResource.data
            if (movieDetail != null) {
                val state = !movieDetail.isFavorite
                filmRepository.setMovieFavorite(movieDetail, state)
            }
        }
        if (tvShowResource != null) {
            val tvShowDetail = tvShowResource.data
            if (tvShowDetail != null) {
                val state = !tvShowDetail.isFavorite
                filmRepository.setTVShowFavorite(tvShowDetail, state)
            }
        }
    }
}