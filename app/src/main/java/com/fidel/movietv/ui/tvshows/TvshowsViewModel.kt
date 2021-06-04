package com.fidel.movietv.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fidel.movietv.data.source.local.entity.TVShowEntity
import com.fidel.movietv.data.FilmRepository
import com.fidel.movietv.vo.Resource

class TvshowsViewModel(private val filmRepository: FilmRepository) : ViewModel(){
    fun getTvShows() = filmRepository.getAllTvShows()
}