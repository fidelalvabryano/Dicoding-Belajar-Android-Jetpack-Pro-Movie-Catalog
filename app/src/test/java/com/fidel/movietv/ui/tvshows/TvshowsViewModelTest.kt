package com.fidel.movietv.ui.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fidel.movietv.data.source.local.entity.TVShowEntity
import com.fidel.movietv.data.FilmRepository
import com.fidel.movietv.utils.DataDummy
import com.fidel.movietv.vo.Resource
import junit.framework.TestCase
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class TvshowsViewModelTest {

    private lateinit var viewModel: TvshowsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var filmRepository: FilmRepository

    @Mock
    private lateinit var observer: Observer<Resource<List<TVShowEntity>>>

    @Before
    fun setUp() {
        viewModel = TvshowsViewModel(filmRepository)
    }

    @Test
    fun getTvShows() {
        val dummyTvShows = Resource.success(DataDummy.generateDummyTvshows())
        val tvShows = MutableLiveData<Resource<List<TVShowEntity>>>()
        tvShows.value = dummyTvShows

        `when`(filmRepository.getAllTvShows()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getTvShows().value?.data
        verify(filmRepository).getAllTvShows()
        TestCase.assertNotNull(tvShowEntities)
        TestCase.assertEquals(10, tvShowEntities?.size)
    }
}