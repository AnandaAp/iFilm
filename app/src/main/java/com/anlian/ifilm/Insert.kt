package com.anlian.ifilm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.anlian.ifilm.api.RetrofitConnection
import com.anlian.ifilm.databinding.FragmentInsertBinding
import com.anlian.ifilm.model.DefaultResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_insert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Insert : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentInsertBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInsertBinding.inflate(layoutInflater)
        val view = binding.root

        insertData()

        // Inflate the layout for this fragment
        return view
    }

    private fun insertData() {
        binding.confirmBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val title = binding.insertTitleField.text.toString().trim()
            val genre = binding.insertGenreField.text.toString().trim()
            val rating = binding.insertRatingField.text.toString().toFloat()
            val query = "insert_movie"
            RetrofitConnection
                .getService()
                .insertMovie(title,genre,rating,query)
                .enqueue(object : Callback<DefaultResponse>{
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        Toast.makeText(
                            requireActivity(),
                            "Insert Movie Success",
                            Toast.LENGTH_LONG
                        ).show()
                        Thread.sleep(2)
                        progressBar.visibility = View.INVISIBLE
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

}