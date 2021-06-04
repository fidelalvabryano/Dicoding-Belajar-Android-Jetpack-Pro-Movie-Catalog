package com.fidel.movietv.utils

import android.content.Context
import com.fidel.movietv.data.source.remote.response.MovieResponse
import com.fidel.movietv.data.source.remote.response.TVShowResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class JsonHelper(private val context: Context) {
    private fun parsingFileToString(fileName: String): String? {
        return try {
            val `is` = context.assets.open(fileName)
            val buffer = ByteArray(`is`.available())
            `is`.read(buffer)
            `is`.close()
            String(buffer)

        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }

    fun loadMovies(): List<MovieResponse> {
        val list = ArrayList<MovieResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("FilmResponse.json").toString())
            val listArray = responseObject.getJSONArray("movies")
            for (i in 0 until listArray.length()) {
                val film = listArray.getJSONObject(i)

                val filmId = film.getString("filmId")
                val title = film.getString("title")
                val overview = film.getString("overview")
                val duration = film.getString("duration")
                val year = film.getString("year")
                val image = film.getString("image")

                val filmResponse = MovieResponse(filmId, title, overview, duration, year, image)
                list.add(filmResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return list
    }

    fun loadDetailMovie(filmId: String) : MovieResponse {
        lateinit var filmResponse: MovieResponse
        try {
            val responseObject = JSONObject(parsingFileToString("FilmResponse.json").toString())
            val listArray = responseObject.getJSONArray("movies")
            for (i in 0 until listArray.length()) {
                val film = listArray.getJSONObject(i)
                if (filmId == film.getString("filmId")) {
                    val movieFilmId = film.getString("filmId")
                    val title = film.getString("title")
                    val overview = film.getString("overview")
                    val duration = film.getString("duration")
                    val year = film.getString("year")
                    val image = film.getString("image")

                    filmResponse = MovieResponse(movieFilmId, title, overview, duration, year, image)
                }
            }
        }catch (e: JSONException) {
            e.printStackTrace()
        }
        return filmResponse
    }

    fun loadTvShows(): List<TVShowResponse> {
        val list = ArrayList<TVShowResponse>()
        try {
            val responseObject = JSONObject(parsingFileToString("FilmResponse.json").toString())
            val listArray = responseObject.getJSONArray("tvShows")
            for (i in 0 until listArray.length()) {
                val film = listArray.getJSONObject(i)

                val filmId = film.getString("filmId")
                val title = film.getString("title")
                val overview = film.getString("overview")
                val duration = film.getString("duration")
                val year = film.getString("year")
                val image = film.getString("image")

                val tvShowResponse = TVShowResponse(filmId, title, overview, duration, year, image)
                list.add(tvShowResponse)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return list
    }

    fun loadDetailTVShow(filmId: String) : TVShowResponse {
        lateinit var filmResponse: TVShowResponse
        try {
            val responseObject = JSONObject(parsingFileToString("FilmResponse.json").toString())
            val listArray = responseObject.getJSONArray("tvShows")
            for (i in 0 until listArray.length()) {
                val film = listArray.getJSONObject(i)
                if (filmId == film.getString("filmId")) {
                    val tvShowFilmId = film.getString("filmId")
                    val title = film.getString("title")
                    val overview = film.getString("overview")
                    val duration = film.getString("duration")
                    val year = film.getString("year")
                    val image = film.getString("image")

                    filmResponse = TVShowResponse(tvShowFilmId, title, overview, duration, year, image)
                }
            }
        }catch (e: JSONException) {
            e.printStackTrace()
        }
        return filmResponse
    }
}