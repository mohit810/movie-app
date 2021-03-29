package com.example.movieapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.adapter.ImageSliderAdapter
import com.example.movieapp.adapter.MainAdapter
import com.example.movieapp.helpers.retrofit.Apis
import com.example.movieapp.helpers.retrofit.RetrofitClient
import com.example.movieapp.helpers.utils.NetworkState
import com.example.movieapp.helpers.utils.repository.MoviePagedListRepository
import com.example.movieapp.viewmodels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var movielist: ArrayList<String> = arrayListOf("")
    private lateinit var viewModel: MainActivityViewModel
    lateinit var movieRepository: MoviePagedListRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
    }

    private fun setupUI() {
        val apiService: Apis = RetrofitClient.getRetrofit()

        movieRepository = MoviePagedListRepository(apiService)

        viewModel = setupViewModel()

        setUpMovieListAdapter()
    }

    private fun setupViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(movieRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }

    private fun setUpSliderMovieBanner() {
        val sliderAdapter = ImageSliderAdapter(this, movielist)
        sliderViewPager.offscreenPageLimit = 1
        sliderViewPager.adapter = sliderAdapter
        viewModel.movieBannerList.observe(this, Observer {
            movielist = it
            sliderAdapter.addData(movielist!!)
            sliderAdapter.notifyDataSetChanged()
        })
    }

    private fun setUpMovieListAdapter() {
        val movieAdapter = MainAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.MOVIE_VIEW_TYPE) return 1
                else return 3
            }
        };


        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(false)
        rv_movie_list.adapter = movieAdapter

        viewModel.moviePagedList.observe(this, Observer {
            movieAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE
            now_Showing_txt.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.GONE else View.VISIBLE

            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        })

        setUpSliderMovieBanner()
    }

}