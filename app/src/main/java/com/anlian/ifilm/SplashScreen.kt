package com.anlian.ifilm

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.anlian.ifilm.controller.SharedPreferencesData
import com.anlian.ifilm.databinding.FragmentSplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var userID: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var fullname: String
    private lateinit var picturePath: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return bindView()
    }

    override fun onStart() {
        super.onStart()
        val sharedPreferences = requireActivity()
            .getSharedPreferences(
                SharedPreferencesData.SHARED_PREFERENCE_CODE,
                MODE_PRIVATE
            )
        val isLogin = sharedPreferences
            .getBoolean(
                SharedPreferencesData
                    .SHARED_PREFERENCE_SESSION_KEY,
                false
            )
        if(isLogin){
            userID = sharedPreferences
                .getString(
                    SharedPreferencesData
                        .SHARED_PREFERENCE_ID_KEY,
                    "").toString()
            email = sharedPreferences
                .getString(
                    SharedPreferencesData
                        .SHARED_PREFERENCE_EMAIL_KEY,
                    "").toString()
            password = sharedPreferences
                .getString(
                    SharedPreferencesData
                        .SHARED_PREFERENCE_PASSWORD_KEY,
                    "").toString()
            fullname = sharedPreferences
                .getString(
                    SharedPreferencesData
                        .SHARED_PREFERENCE_FULLNAME_KEY,
                    ""
                ).toString()
            picturePath = sharedPreferences
                .getString(
                    SharedPreferencesData
                        .SHARED_PREFERENCE_PICTURE_KEY,
                    "").toString()
            val direction = SplashScreenDirections
                .actionSplashScreenToHome2()
            findNavController().navigate(direction)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Thread.sleep(3000)
        val direction = SplashScreenDirections
            .actionSplashScreenToHome2()
        findNavController().navigate(direction)
    }

    private fun bindView(): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        return  binding.root
    }
}