package com.fidel.movietv.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.fidel.movietv.data.source.local.entity.MovieEntity
import com.fidel.movietv.data.source.local.entity.TVShowEntity
import com.fidel.movietv.data.source.local.room.FilmDao

class LocalDataSource private constructor(private val mFilmDao: FilmDao){

    companion object {
        private var INSTANCE : LocalDataSource? = null

        fun getInstance(filmDao: FilmDao) : LocalDataSource =
            INSTANCE ?: LocalDataSource(filmDao)
    }

    fun getAllMovies() : LiveData<List<MovieEntity>> = mFilmDao.getAllMovies()

    fun getAllTVShows() : LiveData<List<TVShowEntity>> = mFilmDao.getAllTVShows()

    fun getFavoriteMovies() : DataSource.Factory<Int, MovieEntity> = mFilmDao.getFavoriteMovies()

    fun getFavoriteTVShows() : DataSource.Factory<Int, TVShowEntity> = mFilmDao.getFavoriteTVShows()

    fun getMovieDetail(filmId: String) : LiveData<MovieEntity> = mFilmDao.getMovieDetail(filmId)

    fun getTVShowDetail(filmId: String) : LiveData<TVShowEntity> = mFilmDao.getTVShowDetail(filmId)

    fun insertMovies(movies: List<MovieEntity>) = mFilmDao.insertMovies(movies)

    fun insertTVShows(tvShow: List<TVShowEntity>) = mFilmDao.insertTVShows(tvShow)

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        mFilmDao.updateMovies(movie)
    }

    fun setTVShowFavorite(tvShow: TVShowEntity, newState: Boolean) {
        tvShow.isFavorite = newState
        mFilmDao.updateTVShows(tvShow)
    }

}