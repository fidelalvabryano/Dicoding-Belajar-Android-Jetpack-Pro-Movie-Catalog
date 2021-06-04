package com.fidel.movietv.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fidel.movietv.data.FilmDataSource
import com.fidel.movietv.data.NetworkBoundResource
import com.fidel.movietv.data.source.local.LocalDataSource
import com.fidel.movietv.data.source.local.entity.MovieEntity
import com.fidel.movietv.data.source.local.entity.TVShowEntity
import com.fidel.movietv.data.source.remote.ApiResponse
import com.fidel.movietv.data.source.remote.RemoteDataSource
import com.fidel.movietv.data.source.remote.response.MovieResponse
import com.fidel.movietv.data.source.remote.response.TVShowResponse
import com.fidel.movietv.utils.AppExecutors
import com.fidel.movietv.vo.Resource

class FakeFilmRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors)
    : FilmDataSource {

    override fun getAllMovies(): LiveData<Resource<List<MovieEntity>>> {
        return object : NetworkBoundResource<List<MovieEntity>, List<MovieResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<List<MovieEntity>> =
                localDataSource.getAllMovies()

            override fun shouldFetch(data: List<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> =
                remoteDataSource.getAllMovies()

            public override fun saveCallResult(movieResponses: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in movieResponses) {
                    val movie = MovieEntity(
                        response.filmId,
                        response.title,
                        response.overview,
                        response.duration,
                        response.year,
                        response.image,
                        false)
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getAllTvShows(): LiveData<Resource<List<TVShowEntity>>> {
        return object : NetworkBoundResource<List<TVShowEntity>, List<TVShowResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<List<TVShowEntity>> =
                localDataSource.getAllTVShows()

            override fun shouldFetch(data: List<TVShowEntity>?): Boolean =
                data == null || data.isEmpty()

            public override fun createCall(): LiveData<ApiResponse<List<TVShowResponse>>> =
                remoteDataSource.getAllTVShows()

            public override fun saveCallResult(tvShowResponse: List<TVShowResponse>) {
                val tvShowList = ArrayList<TVShowEntity>()
                for (response in tvShowResponse) {
                    val tvShow = TVShowEntity(
                        response.filmId,
                        response.title,
                        response.overview,
                        response.duration,
                        response.year,
                        response.image,
                        false)
                    tvShowList.add(tvShow)
                }
                localDataSource.insertTVShows(tvShowList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(filmId: String): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> = localDataSource.getMovieDetail(filmId)

            override fun shouldFetch(data: MovieEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> =
                remoteDataSource.getMovieDetail(filmId)

            override fun saveCallResult(data: MovieResponse) {
                val movie = MovieEntity(
                    filmId = data.filmId,
                    title = data.title,
                    overview = data.overview,
                    duration = data.duration,
                    year = data.year,
                    image = data.image
                )
                localDataSource.setMovieFavorite(movie, false)
            }
        }.asLiveData()
    }


    override fun getTVShowDetail(filmId: String): LiveData<Resource<TVShowEntity>> {
        return object : NetworkBoundResource<TVShowEntity, TVShowResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TVShowEntity> = localDataSource.getTVShowDetail(filmId)

            override fun shouldFetch(data: TVShowEntity?): Boolean =
                data == null

            override fun createCall(): LiveData<ApiResponse<TVShowResponse>> =
                remoteDataSource.getTVShowDetail(filmId)

            override fun saveCallResult(data: TVShowResponse) {
                val tvShow = TVShowEntity(
                    filmId = data.filmId,
                    title = data.title,
                    overview = data.overview,
                    duration = data.duration,
                    year = data.year,
                    image = data.image
                )
                localDataSource.setTVShowFavorite(tvShow, false)
            }
        }.asLiveData()
    }

    override fun getFavoriteMovies(): LiveData<List<MovieEntity>> =
        localDataSource.getFavoriteMovies()

    override fun getFavoriteTVShows(): LiveData<List<TVShowEntity>> =
        localDataSource.getFavoriteTVShows()

    override fun setMovieFavorite(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute{ localDataSource.setMovieFavorite(movie, state) }
    }

    override fun setTVShowFavorite(tvShow: TVShowEntity, state: Boolean) {
        appExecutors.diskIO().execute{ localDataSource.setTVShowFavorite(tvShow, state)}
    }
}