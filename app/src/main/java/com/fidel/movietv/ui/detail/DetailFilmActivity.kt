package com.fidel.movietv.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.fidel.movietv.R
import com.fidel.movietv.databinding.ActivityDetailFilmBinding
import com.fidel.movietv.databinding.ContentDetailFilmBinding
import com.fidel.movietv.utils.JsonHelper
import com.fidel.movietv.viewmodel.ViewModelFactory
import com.fidel.movietv.vo.Status

class DetailFilmActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_FILM = "extra_film"
    }

    private lateinit var activityDetailFilmBinding: ActivityDetailFilmBinding
    private lateinit var detailContentBinding: ContentDetailFilmBinding

    private lateinit var viewModel: DetailFilmViewModel
    private lateinit var movieSelected: String
    private lateinit var tvShowSelected: String

    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailFilmBinding = ActivityDetailFilmBinding.inflate(layoutInflater)
        detailContentBinding = activityDetailFilmBinding.detailContent

        setContentView(activityDetailFilmBinding.root)

        setSupportActionBar(activityDetailFilmBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailFilmViewModel::class.java]
        val extras = intent.extras


        if (extras != null) {
            val filmId = extras.getString(EXTRA_FILM)
            if (filmId != null && filmId.startsWith("MOV")) {
                viewModel.setSelectedMovie(filmId)
                viewModel.getDetailMovie.observe(this, { getDetailMoviesResource ->
                    if (getDetailMoviesResource != null) {
                        when (getDetailMoviesResource.status) {
                            Status.LOADING -> activityDetailFilmBinding.progressBar.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                activityDetailFilmBinding.progressBar.visibility = View.GONE
                                for (movie in JsonHelper(this).loadMovies()) {
                                    if (movie.filmId == filmId) {
                                        detailContentBinding.titletext.text = movie.title
                                        detailContentBinding.yeartext.text = movie.year
                                        detailContentBinding.overviewtext.text = movie.overview
                                        detailContentBinding.durationtext.text = movie.duration
                                        Glide.with(this)
                                            .load(movie.image)
                                            .into(detailContentBinding.movieimage)

                                        movieSelected = movie.filmId
                                    }
                                }
                            }
                            Status.ERROR -> {
                                activityDetailFilmBinding.progressBar.visibility = View.GONE
                                Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            } else if (filmId != null && filmId.startsWith("TVS")) {
                viewModel.setSelectedTVShow(filmId)
                viewModel.getDetailTVShow.observe(this, { getDetailTVShowResource ->
                    if (getDetailTVShowResource != null) {
                        when (getDetailTVShowResource.status) {
                            Status.LOADING -> activityDetailFilmBinding.progressBar.visibility = View.VISIBLE
                            Status.SUCCESS -> {
                                activityDetailFilmBinding.progressBar.visibility = View.GONE
                                for (tvShow in JsonHelper(this).loadTvShows()) {
                                    if (tvShow.filmId == filmId) {
                                        detailContentBinding.titletext.text = tvShow.title
                                        detailContentBinding.yeartext.text = tvShow.year
                                        detailContentBinding.overviewtext.text = tvShow.overview
                                        detailContentBinding.durationtext.text = tvShow.duration
                                        Glide.with(this)
                                            .load(tvShow.image)
                                            .into(detailContentBinding.movieimage)
                                        tvShowSelected = tvShow.filmId
                                    }
                                }
                            }
                            Status.ERROR -> {
                                activityDetailFilmBinding.progressBar.visibility = View.GONE
                                Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu

        viewModel.getDetailMovie.observe(this, { movie ->
            if (movie != null) {
                when (movie.status) {
                    Status.LOADING -> activityDetailFilmBinding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> if (movie.data != null) {
                        activityDetailFilmBinding.progressBar.visibility = View.GONE
                        val state = movie.data.isFavorite
                        setFavoriteFilm(state)
                    }
                    Status.ERROR -> {
                        activityDetailFilmBinding.progressBar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        viewModel.getDetailTVShow.observe(this, { tvShow ->
            if (tvShow != null) {
                when (tvShow.status) {
                    Status.LOADING -> activityDetailFilmBinding.progressBar.visibility = View.VISIBLE
                    Status.SUCCESS -> if (tvShow.data != null) {
                        activityDetailFilmBinding.progressBar.visibility = View.GONE
                        val state = tvShow.data.isFavorite
                        setFavoriteFilm(state)
                    }
                    Status.ERROR -> {
                        activityDetailFilmBinding.progressBar.visibility = View.GONE
                        Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            viewModel.setFavoriteFilm()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteFilm(state: Boolean) {
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_nofavorite_white)
        }
    }
}