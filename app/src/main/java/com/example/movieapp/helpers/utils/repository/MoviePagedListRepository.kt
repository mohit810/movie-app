package com.example.movieapp.helpers.utils.repository

import MovieDetails
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.movieapp.helpers.retrofit.Apis
import com.example.movieapp.helpers.utils.Constants.POST_PER_PAGE
import com.example.movieapp.helpers.utils.NetworkState
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService: Apis) {

    lateinit var moviePagedList: LiveData<PagedList<MovieDetails>>
    lateinit var moviesDataSourceFactory: MovieDataSourceFactory
    var mutablemovieBannerList = MutableLiveData<ArrayList<String>>()
    lateinit var livemovieBannerList: LiveData<ArrayList<String>>

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<MovieDetails>> {
        moviesDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            moviesDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState
        )
    }

    fun getMovieBannerList() {
        mutablemovieBannerList.value = arrayListOf(
            "https://image.tmdb.org/t/p/w500/iopYFB1b6Bh7FWZh3onQhph1sih.jpg",
            "https://image.tmdb.org/t/p/w500/fev8UFNFFYsD5q7AcYS8LyTzqwl.jpg",
            "https://image.tmdb.org/t/p/w500/lOSdUkGQmbAl5JQ3QoHqBZUbZhC.jpg"
        )
        livemovieBannerList = mutablemovieBannerList
    }
}