package com.anlian.ifilm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.anlian.ifilm.databinding.FragmentDetailProfileBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_detail_profile.*

class DetailProfile : Fragment() {
    private lateinit var binding: FragmentDetailProfileBinding
    private val args: DetailProfileArgs by navArgs()
    private val BASE_URL = "http://192.168.1.8/ilist/profiles/pictures/"
//    private val TAG = "DetailProfile"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData()
    }

    private fun moveToChangePassword(
        userID: String,
        password: String,
        email: String,
        fullname: String,
        picturePath: String
    ) {
        val direction = DetailProfileDirections
            .actionDetailProfileToChangePassword(
                userID,
                password,
                email,
                fullname,
                picturePath
            )
        findNavController().navigate(direction)
    }

    private fun bindData() {
        val userID = args.userID
        val email = args.email
        val password = args.password
        val fullname = args.fullname
        val picturePath = args.picturePath
        Glide
            .with(requireActivity())
            .load("$BASE_URL$picturePath")
            .apply(RequestOptions()
                .override(200, 200))
            .into(userPicture)
        fullnameValue.text = fullname
        emailValue.text = email
        passwordValue.text = password

        moveToChangePassword(
            userID,
            password,
            email,
            fullname,
            picturePath
        )
    }

    private fun bindingView() {
        binding = FragmentDetailProfileBinding.inflate(layoutInflater)
    }
}