package com.fidel.movietv.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fidel.movietv.data.source.local.LocalDataSource
import com.fidel.movietv.data.source.local.entity.MovieEntity
import com.fidel.movietv.data.source.local.entity.TVShowEntity
import com.fidel.movietv.data.source.remote.RemoteDataSource
import com.fidel.movietv.data.source.remote.response.MovieResponse
import com.fidel.movietv.data.source.remote.response.TVShowResponse
import com.fidel.movietv.ui.detail.DetailFilmViewModel
import com.fidel.movietv.ui.movies.MoviesViewModel
import com.fidel.movietv.ui.tvshows.TvshowsViewModel
import com.fidel.movietv.utils.AppExecutors
import com.fidel.movietv.utils.DataDummy
import com.fidel.movietv.utils.LiveDataTestUtil
import com.fidel.movietv.vo.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.doAnswer
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


class FilmRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val filmRepository = FakeFilmRepository(remote, local, appExecutors)

    private val movieResponse = DataDummy.generateRemoteDummyMovies()
    private val tvShowResponse = DataDummy.generateRemoteDummyTvshows()

    private val movieDetail = DataDummy.getDetailMovie()
    private val movieTVShow = DataDummy.getDetailTVShow()

    private val movieFilmId = movieResponse[0].filmId
    private val tvShowFilmId = tvShowResponse[0].filmId

    @Test
    fun getAllMovies() {
        val dummyMovies = MutableLiveData<List<MovieEntity>>()
        dummyMovies.value = DataDummy.generateDummyMovies()
        `when`(local.getAllMovies()).thenReturn(dummyMovies)

        val movieEntities = LiveDataTestUtil.getValue(filmRepository.getAllMovies())
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponse.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTvShows() {
        val dummyTVShows = MutableLiveData<List<TVShowEntity>>()
        dummyTVShows.value = DataDummy.generateDummyTvshows()
        `when`(local.getAllTVShows()).thenReturn(dummyTVShows)

        val tvShowEntities = LiveDataTestUtil.getValue(filmRepository.getAllTvShows())
        verify(local).getAllTVShows()
        assertNotNull(tvShowEntities.data)
        assertEquals(movieResponse.size.toLong(), tvShowEntities.data?.size?.toLong())
    }


    @Test
    fun getMovieDetail() {
        val dummyMovieDetail = MutableLiveData<MovieEntity>()
        dummyMovieDetail.value = DataDummy.getDetailMovie()
        `when`(local.getMovieDetail(movieFilmId)).thenReturn(dummyMovieDetail)

        val movieEntities = LiveDataTestUtil.getValue(filmRepository.getMovieDetail(movieFilmId))
        verify(local).getMovieDetail(movieFilmId)
        assertNotNull(movieEntities.data)
    }

    @Test
    fun getTVShowDetail() {
        val dummyTVShowDetail = MutableLiveData<TVShowEntity>()
        dummyTVShowDetail.value = DataDummy.getDetailTVShow()
        `when`(local.getTVShowDetail(tvShowFilmId)).thenReturn(dummyTVShowDetail)

        val tvShowEntities = LiveDataTestUtil.getValue(filmRepository.getTVShowDetail(tvShowFilmId))
        verify(local).getTVShowDetail(tvShowFilmId)
        assertNotNull(tvShowEntities.data)
    }

    @Test
    fun getFavoriteMovie() {
        val dummyMovies = MutableLiveData<List<MovieEntity>>()
        dummyMovies.value = DataDummy.generateDummyMovies()
        `when`(local.getFavoriteMovies()).thenReturn(dummyMovies)

        val movieEntities = LiveDataTestUtil.getValue(filmRepository.getFavoriteMovies())
        verify(local).getFavoriteMovies()
        assertNotNull(movieEntities)
        assertEquals(movieResponse.size.toLong(), movieEntities.size.toLong())
    }

    @Test
    fun getFavoriteTVShow() {
        val dummyTVShows = MutableLiveData<List<TVShowEntity>>()
        dummyTVShows.value = DataDummy.generateDummyTvshows()
        `when`(local.getFavoriteTVShows()).thenReturn(dummyTVShows)

        val tvShowEntities = LiveDataTestUtil.getValue(filmRepository.getFavoriteTVShows())
        verify(local).getFavoriteTVShows()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponse.size.toLong(), tvShowEntities.size.toLong())
    }


}
