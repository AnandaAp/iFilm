package com.anlian.ifilm.architecture

import com.anlian.ifilm.api.RetrofitConnection
import com.anlian.ifilm.controller.Adapter
import com.anlian.ifilm.databinding.FragmentHomeBinding
import com.anlian.ifilm.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Presenter(private var view: MainView) {
    fun getData(){
        RetrofitConnection
            .getService()
            .getMovie()
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful){
                        val result = response.body()?.data
                        view.resultSuccess(result)
                        println(response.body()?.data)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    println("There is no data in database or check your internet connection")
                    view.resultFailed(t)
                }
            })
    }
}