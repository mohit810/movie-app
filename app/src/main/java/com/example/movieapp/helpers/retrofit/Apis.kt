package com.example.movieapp.helpers.retrofit

import Base
import com.example.movieapp.helpers.retrofit.dataclass.MovieDetail
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Apis {
    @GET("popular")
    fun popularMovies(@Query("page") page: Int): Single<Base>

    @GET("{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetail>
}
