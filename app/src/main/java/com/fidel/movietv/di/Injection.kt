package com.fidel.movietv.di

import android.content.Context
import com.fidel.movietv.data.FilmRepository
import com.fidel.movietv.data.source.local.LocalDataSource
import com.fidel.movietv.data.source.local.room.FilmDatabase
import com.fidel.movietv.data.source.remote.RemoteDataSource
import com.fidel.movietv.utils.AppExecutors
import com.fidel.movietv.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): FilmRepository {

        val database = FilmDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(database.filmDao())
        val appExecutors = AppExecutors()

        return FilmRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}