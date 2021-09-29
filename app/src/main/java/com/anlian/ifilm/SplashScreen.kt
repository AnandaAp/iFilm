package com.anlian.ifilm

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anlian.ifilm.controller.SharedPreferencesData
import com.anlian.ifilm.databinding.FragmentSplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        checkSession()
        return bindView()
    }

    private fun checkSession() {
        val sharedPreferences = SharedPreferencesData(requireActivity())
        val isLogin = sharedPreferences
            .getValueBoolean(SharedPreferencesData.SHARED_PREFERENCE_SESSION_KEY)
        if(isLogin){
//            findNavController().navigate(R.id.action_splashScreen_to_home2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Thread.sleep(3000)
//        findNavController().navigate(R.id.action_splashScreen_to_home2)
    }

    private fun bindView(): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        return  binding.root
    }
}