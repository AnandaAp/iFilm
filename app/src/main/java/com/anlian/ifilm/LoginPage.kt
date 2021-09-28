package com.anlian.ifilm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.anlian.ifilm.api.RetrofitConnection
import com.anlian.ifilm.databinding.FragmentLoginPageBinding
import com.anlian.ifilm.model.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPage : Fragment() {
    private lateinit var binding: FragmentLoginPageBinding
    private lateinit var email:String
    private lateinit var password:String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = bindngView()
        signInProcess()
        return view
    }

    private fun signInProcess() {
        val function = "sign_in"
        binding.signInBtnConfirm.setOnClickListener{
            getInputData() // digunakan untuk mengambil input dari edit text box
            RetrofitConnection
                .getService()
                .signIn(function,email,password)
                .enqueue(object : Callback<ProfileResponse>{
                    override fun onResponse(
                        call: Call<ProfileResponse>,
                        response: Response<ProfileResponse>
                    ) {
                        if (response.isSuccessful){
                            val email = response
                                .body()
                                ?.profile
                                ?.get(0)
                                ?.email
                            val pass = response
                                .body()
                                ?.profile
                                ?.get(0)
                                ?.password
                            val fullname = response
                                .body()
                                ?.profile
                                ?.get(0)
                                ?.name
                            val picturePath = response
                                .body()
                                ?.profile
                                ?.get(0)
                                ?.profilePicturePath
                            val userID = response
                                .body()
                                ?.profile
                                ?.get(0)
                                ?.iD
                            val direction = LoginPageDirections
                                .actionLoginPageToHome2(
                                    userID!!,
                                    fullname!!,
                                    email!!,
                                    pass!!,
                                    picturePath!!
                                )
                            Toast
                                .makeText(
                                    requireActivity(),
                                    getString(R.string.sign_in_success),
                                    Toast.LENGTH_LONG)
                                .show()
                            Thread.sleep(2000)
                            findNavController().navigate(direction)
                        }
                        else{
                            Toast
                                .makeText(
                                    requireActivity(),
                                    getString(R.string.wrong_email),
                                    Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                        Toast
                            .makeText(
                                requireActivity(),
                                getString(R.string.wrong_email),
                                Toast.LENGTH_SHORT)
                            .show()
                        Log.d("error sign in", "onFailure: ${t.message}")
                    }

                })
        }
    }

    private fun getInputData() {
        email = binding.emailSignInBoxField.editText?.text.toString().trim()
        password = binding.passwordSignInBoxField.editText?.text.toString().trim()
    }

    private fun bindngView(): View {
        binding = FragmentLoginPageBinding.inflate(layoutInflater)
        return binding.root
    }
}