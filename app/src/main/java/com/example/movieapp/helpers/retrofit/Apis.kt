package com.example.movieapp.helpers.retrofit

import Base
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface Apis {
    @GET("popular")
    fun popularMovies(@Query("page") page:Int): Single<Base>
}
