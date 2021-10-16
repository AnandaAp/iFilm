package com.anlian.ifilm.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConnection {
//    val BASE_URL = "http://192.168.1.12/iList/"
//    fun getInterseptor(): OkHttpClient {
//        val logging = HttpLoggingInterceptor()
//        logging.level = HttpLoggingInterceptor.Level.BODY
//        return OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .build()
//    }
//    fun getRetrofit(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(getInterseptor())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
    companion object {
        private const val BASE_URL = "http://192.168.1.8/iList/"
        private const val BASE_URL_RUMAH = "http://192.168.1.2/iList/"
        private fun getInterseptor(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        }
        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL_RUMAH)
                .client(getInterseptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        fun getService(): Service = getRetrofit().create(Service::class.java)
    }
}