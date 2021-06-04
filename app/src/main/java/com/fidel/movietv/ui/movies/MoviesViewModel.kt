package com.fidel.movietv.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fidel.movietv.data.source.local.entity.MovieEntity
import com.fidel.movietv.data.FilmRepository
import com.fidel.movietv.vo.Resource

class MoviesViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getMovies() : LiveData<Resource<List<MovieEntity>>> = filmRepository.getAllMovies()
}