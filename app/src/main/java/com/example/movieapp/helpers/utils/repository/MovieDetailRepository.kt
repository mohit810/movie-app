package com.example.movieapp.helpers.utils.repository

import androidx.lifecycle.LiveData
import com.example.movieapp.helpers.retrofit.Apis
import com.example.movieapp.helpers.retrofit.dataclass.MovieDetail
import com.example.movieapp.helpers.utils.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailRepository(private val apis: Apis) {

    lateinit var movieDetailNetworkDataSource: MovieDetailNetworkDataSource

    fun fetchMovieDetail(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetail> {

        movieDetailNetworkDataSource = MovieDetailNetworkDataSource(apis, compositeDisposable)
        movieDetailNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailNetworkState(): LiveData<NetworkState> {
        return movieDetailNetworkDataSource.networkState
    }

}