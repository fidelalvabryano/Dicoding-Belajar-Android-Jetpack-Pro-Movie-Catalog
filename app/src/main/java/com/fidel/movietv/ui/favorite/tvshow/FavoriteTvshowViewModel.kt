package com.fidel.movietv.ui.favorite.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.fidel.movietv.data.source.local.entity.TVShowEntity
import com.fidel.movietv.data.FilmRepository

class FavoriteTvshowViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getFavoriteTvShows() : LiveData<PagedList<TVShowEntity>> = filmRepository.getFavoriteTVShows()

    fun setFavoriteTVShow(tvShowEntity: TVShowEntity) {
        val newState = !tvShowEntity.isFavorite
        filmRepository.setTVShowFavorite(tvShowEntity, newState)
    }
}