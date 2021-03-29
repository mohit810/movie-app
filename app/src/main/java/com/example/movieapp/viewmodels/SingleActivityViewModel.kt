package com.example.movieapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.helpers.retrofit.dataclass.MovieDetail
import com.example.movieapp.helpers.utils.NetworkState
import com.example.movieapp.helpers.utils.repository.MovieDetailRepository
import io.reactivex.disposables.CompositeDisposable

class SingleActivityViewModel(
    private val movieDetailRepository: MovieDetailRepository,
    movieId: Int
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetail: LiveData<MovieDetail> by lazy {
        movieDetailRepository.fetchMovieDetail(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieDetailRepository.getMovieDetailNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}