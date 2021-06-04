package com.fidel.movietv.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.fidel.movietv.data.source.local.entity.MovieEntity
import com.fidel.movietv.data.source.local.entity.TVShowEntity

@Dao
interface FilmDao {
    @Query("SELECT * FROM movieentities")
    fun getAllMovies() : LiveData<List<MovieEntity>>

    @Query("SELECT * FROM tvshowentities")
    fun getAllTVShows() : LiveData<List<TVShowEntity>>

    @Query("SELECT * FROM movieentities WHERE isFavorite = 1")
    fun getFavoriteMovies() : DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM tvshowentities WHERE isFavorite = 1")
    fun getFavoriteTVShows() : DataSource.Factory<Int, TVShowEntity>

    @Transaction
    @Query("SELECT * FROM movieentities WHERE filmId = :filmId")
    fun getMovieDetail(filmId: String) : LiveData<MovieEntity>

    @Transaction
    @Query("SELECT * FROM tvshowentities WHERE filmId = :filmId")
    fun getTVShowDetail(filmId: String) : LiveData<TVShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTVShows(tvShows: List<TVShowEntity>)

    @Update
    fun updateMovies(movies: MovieEntity)

    @Update
    fun updateTVShows(tvShows: TVShowEntity)

}