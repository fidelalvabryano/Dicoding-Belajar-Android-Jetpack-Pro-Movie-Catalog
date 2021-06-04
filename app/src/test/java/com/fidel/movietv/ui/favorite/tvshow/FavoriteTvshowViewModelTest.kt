package com.fidel.movietv.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fidel.movietv.data.FilmRepository
import com.fidel.movietv.data.source.local.entity.MovieEntity
import com.fidel.movietv.data.source.local.entity.TVShowEntity
import com.fidel.movietv.ui.favorite.movie.FavoriteMovieViewModel
import com.fidel.movietv.utils.DataDummy
import com.fidel.movietv.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteTvshowViewModelTest {

    private lateinit var viewModel: FavoriteTvshowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<Resource<List<TVShowEntity>>>

    @Before
    fun setUp() {
        viewModel = FavoriteTvshowViewModel(filmRepository)
    }

    @Test
    fun getFavoriteTVShow() {
        val dummyTVShows = DataDummy.generateDummyTvshows()
        val tvShows = MutableLiveData<List<TVShowEntity>>()
        tvShows.value = dummyTVShows

        Mockito.`when`(filmRepository.getFavoriteTVShows()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getFavoriteTvShows().value
        verify(filmRepository).getFavoriteTVShows()
        assertNotNull(tvShowEntities)
        assertEquals(10, tvShowEntities?.size)

    }

}