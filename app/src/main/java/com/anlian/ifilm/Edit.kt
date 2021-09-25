package com.anlian.ifilm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.anlian.ifilm.api.RetrofitConnection
import com.anlian.ifilm.databinding.FragmentEditBinding
import com.anlian.ifilm.model.DefaultResponse
import com.anlian.ifilm.model.MovieResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Edit : BottomSheetDialogFragment() {
    private val args: EditArgs by navArgs()
    private lateinit var binding: FragmentEditBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(layoutInflater)
        val view = binding.root

        bindData()
        // Inflate the layout for this fragment
        return view
    }

    private fun bindData() {
        val id = args.id
        val title = args.title
        val genre = args.genre
        val rating = args.popularity

        println(id)
        println(title)
        println(genre)
        println(rating)
        binding.editTitleField.hint = title
        binding.genreEditField.hint = genre
        binding.editRatingField.hint = rating

        binding.editBtnConfirm.setOnClickListener {
            startRetrofit(id,title,genre,rating)
        }
    }

    private fun startRetrofit(id: String, title: String, genre: String, rating: String) {
        val function = "update_movie"
        RetrofitConnection
            .getService()
            .updateMovie(id, title, genre,rating,function)
            .enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    if (response.isSuccessful){
                        Toast.makeText(
                            requireActivity(),
                            "Edit Success",
                            Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(
                            requireActivity(),
                            "Reponse Gagal",
                            Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    println("Failed to")
                    Toast.makeText(
                        requireActivity(),
                        t.localizedMessage,
                        Toast.LENGTH_LONG).show()
                }
            })
    }
}