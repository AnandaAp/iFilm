package com.anlian.ifilm.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface Service {
    @GET("get_movie")
    fun getMovie(): Call <MovieResponse>
    @POST("insert_movie")
    fun insertMovie()
}