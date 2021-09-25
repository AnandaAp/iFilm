package com.anlian.ifilm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anlian.ifilm.api.RetrofitConnection
import com.anlian.ifilm.controller.Adapter
import com.anlian.ifilm.databinding.FragmentHomeBinding
import com.anlian.ifilm.model.DataItem
import com.anlian.ifilm.model.MovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Home : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: Adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root
        adapter = Adapter(requireActivity(), arrayListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapter
        startRetrofit()
        insertData()

        // Inflate the layout for this fragment
        return view
    }

    private fun insertData() {
        binding.addMovieBtn.setOnClickListener{
            findNavController().navigate(R.id.action_home2_to_insert)
        }
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
                        val result = response.body()?.data
                        showData(result)
                        println(response.body()?.data)
                    }else{
                        Toast.makeText(
                            requireActivity(),
                            "Reponse Gagal",
                            Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    println("Failed to")
                    Toast.makeText(
                        requireActivity(),
                        t.localizedMessage,
                        Toast.LENGTH_LONG).show()
                }

            })
    }
    private fun showData(result: List<DataItem?>?) {
        updateAdapter(result)
    }

    private fun updateAdapter(result: List<DataItem?>?) {
        adapter.setData(result as ArrayList<DataItem>)
    }
}