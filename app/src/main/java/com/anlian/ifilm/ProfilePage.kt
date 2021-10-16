package com.anlian.ifilm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.anlian.ifilm.controller.SharedPreferencesData
import com.anlian.ifilm.databinding.FragmentProfileDetailBinding
import kotlinx.android.synthetic.main.fragment_profile_detail.*

class ProfilePage : Fragment() {
    private lateinit var binding: FragmentProfileDetailBinding
    private val args: ProfilePageArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moveToDetailProfile()
        moveToEditProfile()
        signOut()
    }

    private fun moveToDetailProfile(){
        val userID = args.userID
        val email = args.email
        val password = args.password
        val fullname = args.fullname
        val picturePath = args.picturePath
        detailProfileBtn.setOnClickListener{
            val direction = ProfilePageDirections
                .actionProfilePageToDetailProfile(
                    userID,email,password,picturePath,fullname
                )
            findNavController().navigate(direction)
        }
    }

    private fun signOut() {
        signOutBtn.setOnClickListener {
            val preferences = SharedPreferencesData(requireActivity())
            preferences.clearSharedPreference()
            val intent = Intent(requireActivity(),MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun bindingView(){
        binding = FragmentProfileDetailBinding.inflate(layoutInflater)
    }
    private fun moveToEditProfile(){
        editProfileBtn.setOnClickListener {
            val id = args.userID
            val hardwareID = args.hardwareID
            val direction = ProfilePageDirections
                .actionProfileDetailToEditProfile(id,hardwareID)
            findNavController()
                .navigate(direction)
        }
    }
}