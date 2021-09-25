package com.anlian.ifilm.controller

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anlian.ifilm.databinding.DetailMovieRecyclerViewBinding
import com.anlian.ifilm.model.DataItem
import com.anlian.ifilm.model.MovieResponse
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
            holder.binding.numberTxt.text = position.toString()
            holder.binding.titleMovieTxt.text = data.get(position).title
            holder.binding.genreTxt.text = data.get(position).genre
            holder.binding.popularityTxt.text = "Rating : ${
                data.get(position).rating
            }"
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
}