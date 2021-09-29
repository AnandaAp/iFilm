package com.anlian.ifilm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.anlian.ifilm.controller.SharedPreferencesData
import com.anlian.ifilm.databinding.FragmentProfileDetailBinding
import kotlinx.android.synthetic.main.fragment_profile_detail.*

class ProfileDetail : Fragment() {
    private lateinit var binding: FragmentProfileDetailBinding
    private val args: ProfileDetailArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signOut()
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

    fun bindingView(){
        binding = FragmentProfileDetailBinding.inflate(layoutInflater)
    }
}