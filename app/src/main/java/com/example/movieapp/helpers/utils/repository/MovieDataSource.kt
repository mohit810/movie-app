package com.example.movieapp.helpers.utils.repository

import MovieDetails
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.movieapp.helpers.retrofit.Apis
import com.example.movieapp.helpers.utils.Constants.FIRST_PAGE
import com.example.movieapp.helpers.utils.NetworkState
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private val apiService:Apis, private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int, MovieDetails>() {

    private var page = FIRST_PAGE
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieDetails>
    ) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.popularMovies(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.movieList,null,page+1)
                        networkState.postValue(NetworkState.LOADED)
                    },{
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDatasource", it.message.toString())
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieDetails>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.popularMovies(page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.total_pages >= params.key) {
                            callback.onResult(it.movieList,params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        } else {

                        }
                    },{
                        networkState.postValue(NetworkState.ENDOFLIST)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieDetails>) {

    }
}