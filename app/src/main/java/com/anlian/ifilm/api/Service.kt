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
        @Field("title") title: String,
        @Field("genre") genre: String,
        @Field("rating") rating: Float,
        @Query("function") function: String
    ): Call <DefaultResponse>

    @FormUrlEncoded
    @POST("restapi.php")
    fun deleteMovie(
        @Field("id") id: String,
        @Query("function") function: String
    ): Call <DefaultResponse>

    @FormUrlEncoded
    @POST("restapi.php")
    fun updateMovie(
        @Field("id") id: String,
        @Field("title") title: String,
        @Field("genre") genre: String,
        @Field("rating") rating: String,
        @Query("function") function: String
    ): Call <DefaultResponse>
}