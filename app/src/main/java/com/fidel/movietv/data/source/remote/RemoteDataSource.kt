package com.fidel.movietv.data.source.remote

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fidel.movietv.data.source.remote.response.MovieResponse
import com.fidel.movietv.data.source.remote.response.TVShowResponse
import com.fidel.movietv.utils.EspressoIdlingResource
import com.fidel.movietv.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper)  {

    private val handler = Handler(Looper.getMainLooper())

    companion object {

        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper).apply { instance = this }
            }
    }

    fun getAllMovies() : LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        handler.postDelayed({
            resultMovie.value = ApiResponse.success(jsonHelper.loadMovies())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultMovie
    }

    fun getAllTVShows() : LiveData<ApiResponse<List<TVShowResponse>>> {
        EspressoIdlingResource.increment()
        val resultTVShow = MutableLiveData<ApiResponse<List<TVShowResponse>>>()
        handler.postDelayed({
            resultTVShow.value = ApiResponse.success(jsonHelper.loadTvShows())
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultTVShow
    }

    fun getMovieDetail(filmId : String) : LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<MovieResponse>>()
        handler.postDelayed({
            resultDetailMovie.value = ApiResponse.success(jsonHelper.loadDetailMovie(filmId))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultDetailMovie
    }

    fun getTVShowDetail(filmId: String) : LiveData<ApiResponse<TVShowResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailTVShow = MutableLiveData<ApiResponse<TVShowResponse>>()
        handler.postDelayed({
            resultDetailTVShow.value = ApiResponse.success(jsonHelper.loadDetailTVShow(filmId))
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultDetailTVShow
    }
}