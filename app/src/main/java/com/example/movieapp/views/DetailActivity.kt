package com.example.movieapp.views

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.adapter.GenreAdapter
import com.example.movieapp.helpers.retrofit.Apis
import com.example.movieapp.helpers.retrofit.RetrofitClient
import com.example.movieapp.helpers.retrofit.dataclass.MovieDetail
import com.example.movieapp.helpers.utils.Constants.POSTER_BASE_URL
import com.example.movieapp.helpers.utils.NetworkState
import com.example.movieapp.helpers.utils.repository.MovieDetailRepository
import com.example.movieapp.viewmodels.SingleActivityViewModel
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: SingleActivityViewModel
    private lateinit var movieDetailRepository: MovieDetailRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setUpUI()
        var movieId = intent.getIntExtra("id", 1)
        val apis: Apis = RetrofitClient.getRetrofit()
        movieDetailRepository = MovieDetailRepository(apis)

        viewModel = getViewModel(movieId)
        setUpViewModel()
    }

    private fun setUpUI() {
        var back_btn = toolbar.findViewById<ImageView>(R.id.menu_icon)
        var share_btn = toolbar.findViewById<ImageView>(R.id.search_icon)
        back_btn.setImageDrawable(this.getDrawable(R.drawable.ic_arrow_back))
        share_btn.setImageDrawable(this.getDrawable(R.drawable.ic_share))
        back_btn.setOnClickListener {
            finish()
        }
    }

    private fun setUpViewModel() {
        viewModel.movieDetail.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    private fun bindUI(it: MovieDetail) {

        movie_title.text = it.title
        var hours = it.runtime / 60
        var minutes = it.runtime % 60
        movie_details.text =
            hours.toString() + "h " + minutes.toString() + "min | " + it.release_date
        movie_rating.text = it.vote_average.toString()
        movie_review.text = "Reviews"
        movie_overview.text = it.overview

        Glide.with(this)
            .load(POSTER_BASE_URL + it.poster_path)
            .into(movie_poster)

        val genreAdapter = GenreAdapter(this, it.genres)
        genre_recyclerView.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        genre_recyclerView.adapter = genreAdapter
    }

    private fun getViewModel(movieId: Int): SingleActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleActivityViewModel(movieDetailRepository, movieId) as T
            }
        })[SingleActivityViewModel::class.java]
    }

}