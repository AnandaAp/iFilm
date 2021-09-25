package com.anlian.ifilm.controller

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.anlian.ifilm.HomeDirections
import com.anlian.ifilm.api.RetrofitConnection
import com.anlian.ifilm.databinding.DetailMovieRecyclerViewBinding
import com.anlian.ifilm.model.DataItem
import com.anlian.ifilm.model.DefaultResponse
import com.anlian.ifilm.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class Adapter(private val context: Activity, private var data: ArrayList<DataItem>):
    RecyclerView.Adapter<Adapter.ViewHolder>() {
        inner class ViewHolder(val binding: DetailMovieRecyclerViewBinding):
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.ViewHolder {
            return ViewHolder(DetailMovieRecyclerViewBinding
                .inflate(
                    LayoutInflater
                    .from(parent.context), parent,false))
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: Adapter.ViewHolder, position: Int) {
            val title = data[position].title
            val genre = data[position].genre
            val rating = data[position].rating
            val id = data[position].id?.toInt()
            holder.binding.numberTxt.text = (1+position).toString()
            holder.binding.titleMovieTxt.text = title
            holder.binding.genreTxt.text = genre
            holder.binding.popularityTxt.text = "Rating : $rating"
            holder.binding.deleteBtn.setOnClickListener{
                println("hapus")
                id?.let { it1 -> deleteData(it1) }
            }
            holder.binding.editBtn.setOnClickListener{
                println("edit")
                editData(title,genre,rating,id,it)
            }
        }

    private fun editData(title: String?, genre: String?, rating: String?, id: Int?, view: View) {
        val direction = HomeDirections.actionHome2ToEdit(title!!, genre!!, rating!!, id.toString())
        view.findNavController().navigate(direction)
    }

    override fun getItemCount(): Int {
           return data.size
        }
        @SuppressLint("NotifyDataSetChanged")
        fun setData(_data : ArrayList<DataItem>){
            data.clear()
            data = _data
            notifyDataSetChanged()
        }

        fun deleteData(position: Int) {
            RetrofitConnection
                .getService()
                .deleteMovie(position,"delete_movie")
                .enqueue(object : Callback<DefaultResponse>{
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Toast.makeText(
                            context,
                            "Delete Success",
                            Toast.LENGTH_LONG).show()
                        println("Delete Success")
                    }

                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                        println("Failed to")
                        Toast.makeText(
                            context,
                            t.localizedMessage,
                            Toast.LENGTH_LONG).show()
                    }

                })
        }
        private fun startRetrofit() {
            RetrofitConnection
                .getService()
                .getMovie()
                .enqueue(object : Callback<MovieResponse>{
                    override fun onResponse(
                        call: Call<MovieResponse>,
                        response: Response<MovieResponse>
                    ) {
                        if (response.isSuccessful){
                            println(response.body()?.data)
                            data = response.body()?.data as ArrayList<DataItem>
                            notifyDataSetChanged()
                        }else{
                            Toast.makeText(
                                context,
                                "Reponse Gagal",
                                Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        println("Failed to")
                        Toast.makeText(
                            context,
                            t.localizedMessage,
                            Toast.LENGTH_LONG).show()
                    }

                })
        }
}