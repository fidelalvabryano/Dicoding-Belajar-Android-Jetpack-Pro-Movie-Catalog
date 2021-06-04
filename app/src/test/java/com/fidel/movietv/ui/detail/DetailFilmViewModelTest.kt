package com.fidel.movietv.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fidel.movietv.data.source.local.entity.MovieEntity
import com.fidel.movietv.data.source.local.entity.TVShowEntity
import com.fidel.movietv.data.FilmRepository
import com.fidel.movietv.utils.DataDummy
import com.fidel.movietv.vo.Resource
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailFilmViewModelTest {

    private lateinit var viewModel1: DetailFilmViewModel
    private lateinit var viewModel2: DetailFilmViewModel
    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val filmIdMovie = dummyMovie.filmId
    private val dummyTvshow = DataDummy.generateDummyTvshows()[0]
    private val filmIdTvshow = dummyTvshow.filmId

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<TVShowEntity>>

    @Mock
    private lateinit var observer: Observer<Resource<MovieEntity>>

    @Before
    fun setUp() {
        viewModel1 = DetailFilmViewModel(filmRepository)
        viewModel1.setSelectedMovie(filmIdMovie)

        viewModel2 = DetailFilmViewModel(filmRepository)
        viewModel2.setSelectedTVShow(filmIdTvshow)
    }

    @Test
    fun setSelectedMovie() {
        val selectedMovie = viewModel1.setSelectedMovie(dummyMovie.filmId)
        assertNotNull(selectedMovie)
    }

    @Test
    fun setSelectedTVShow() {
        val selectedTVShow = viewModel2.setSelectedTVShow(dummyTvshow.filmId)
        assertNotNull(selectedTVShow)
    }

    @Test
    fun getMovieDetail() {
        val dummyMovieDetail = Resource.success(DataDummy.getDetailMovie())
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyMovieDetail

        `when`(filmRepository.getMovieDetail(filmIdMovie)).thenReturn(movie)

        viewModel1.setSelectedMovie(filmIdMovie)
        viewModel1.getDetailMovie.observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovieDetail)

    }

    @Test
    fun getTVShowDetail() {
        val dummyTVShowDetail = Resource.success(DataDummy.getDetailTVShow())
        val tvShow = MutableLiveData<Resource<TVShowEntity>>()
        tvShow.value = dummyTVShowDetail

        `when`(filmRepository.getTVShowDetail(filmIdTvshow)).thenReturn(tvShow)

        viewModel2.setSelectedTVShow(filmIdTvshow)
        viewModel2.getDetailTVShow.observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTVShowDetail)
    }
}