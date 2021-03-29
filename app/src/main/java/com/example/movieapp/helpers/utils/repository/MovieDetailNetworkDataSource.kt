package com.example.movieapp.helpers.utils.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.helpers.retrofit.Apis
import com.example.movieapp.helpers.retrofit.dataclass.MovieDetail
import com.example.movieapp.helpers.utils.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailNetworkDataSource(
    private val apiservice: Apis,
    private val compositeDisposable: CompositeDisposable
) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState
    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetail>()
    val downloadedMovieResponse: LiveData<MovieDetail>
        get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(movieId: Int) {
        _networkState.postValue(NetworkState.LOADING)
        try {
            compositeDisposable.add(
                apiservice.getMovieDetails(movieId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                        }
                    )
            )
        } catch (e: Exception) {
            Log.e("MovieDetailNetworkDataSource", e.printStackTrace().toString())
        }
    }
}