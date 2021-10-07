package com.anlian.ifilm.api

import com.anlian.ifilm.model.*
import okhttp3.MultipartBody
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
        @Field("id") id: Int,
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

    @GET("Profile.php?function=get_pengguna_by_id&id={id}")
    fun getAccountDetails(@Path("id") id: String): Call <ProfileResponse>

    @GET("Profile.php")
    fun signIn(
        @Query("function") function: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Call <ProfileResponse>

    @FormUrlEncoded
    @POST("restapi.php")
    fun updatePassword(
        @Field("id") id: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Query("function") function: String
    ): Call <DefaultResponse>

    @FormUrlEncoded
    @POST("Profile.php")
    fun insertPengguna(
        @Field("Name") Name: String,
        @Field("Email") Email: String,
        @Field("Password") Password: String,
        @Field("Profile_Picture_Path") Profile_Picture_Path: String,
        @Query("function") function: String
    ): Call <DefaultResponse>

    @Multipart
    @POST("Profile.php?function=upload_picture")
    fun uploadFotoPengguna(@Part body: MultipartBody.Part): Call<UploadImageResponse>

    @FormUrlEncoded
    @POST("Firebase.php")
    fun sendPushNotification(
        @Field("fcm_token") fcm_token: String,
        @Field("title") title : String,
        @Field("message") message : String,
        @Query("function") function: String
    ): Call <PushNotificationResponse>
}