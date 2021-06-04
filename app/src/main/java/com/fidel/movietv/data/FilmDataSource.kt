package com.fidel.movietv.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.fidel.movietv.data.source.local.entity.MovieEntity
import com.fidel.movietv.data.source.local.entity.TVShowEntity
import com.fidel.movietv.vo.Resource

interface FilmDataSource {

    fun getAllMovies(): LiveData<Resource<List<MovieEntity>>>

    fun getAllTvShows(): LiveData<Resource<List<TVShowEntity>>>

    fun getMovieDetail(filmId: String): LiveData<Resource<MovieEntity>>

    fun getTVShowDetail(filmId: String): LiveData<Resource<TVShowEntity>>

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTVShows() : LiveData<PagedList<TVShowEntity>>

    fun setMovieFavorite(movie: MovieEntity, state: Boolean)

    fun setTVShowFavorite(tvShow: TVShowEntity, state: Boolean)

}