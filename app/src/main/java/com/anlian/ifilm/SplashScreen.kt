package com.anlian.ifilm

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.anlian.ifilm.databinding.FragmentSplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
//        Thread.sleep(3000)
        val view = bindView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Thread.sleep(3000)
        val direction = SplashScreenDirections
            .actionSplashScreenToHome2("","","","","")
        findNavController().navigate(direction)
    }

    private fun bindView(): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        return  binding.root
    }
}