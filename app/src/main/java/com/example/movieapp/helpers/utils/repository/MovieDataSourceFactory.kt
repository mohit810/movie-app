package com.example.movieapp.helpers.utils.repository

import MovieDetails
import io.reactivex.disposables.CompositeDisposable
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.movieapp.helpers.retrofit.Apis

class MovieDataSourceFactory(
    private val apiService: Apis,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, MovieDetails>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, MovieDetails> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}