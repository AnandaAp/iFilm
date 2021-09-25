package com.anlian.ifilm.api

import com.anlian.ifilm.model.DefaultResponse
import com.anlian.ifilm.model.MovieResponse
import retrofit2.Call
import retrofit2.http.*

interface Service {
    @GET("restapi.php?function=get_movie")
    fun getMovie(): Call <MovieResponse>
    @FormUrlEncoded
    @POST("restapi.php")
    fun insertMovie(
        @Field("id") id : Int,
        @Field("title") title: String,
        @Field("genre") genre: String,
        @Field("rating") rating: Float,
        @Query("function") function: String
    ): Call <DefaultResponse>
}