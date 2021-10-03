package com.anlian.ifilm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.anlian.ifilm.api.RetrofitConnection
import com.anlian.ifilm.controller.SharedPreferencesData
import com.anlian.ifilm.controller.SharedPreferencesData.Companion.SHARED_PREFERENCE_PASSWORD_KEY
import com.anlian.ifilm.databinding.FragmentChangePasswordBinding
import com.anlian.ifilm.model.DefaultResponse
import kotlinx.android.synthetic.main.fragment_change_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePassword : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding
    private val args: ChangePasswordArgs by navArgs()
    private var userID = ""
    private var email = ""
    private var password = ""
    private var fullname = ""
    private var picturePath = ""
    private val TAG = "CHANGE PASSWORD"

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
        inputProcess()
    }

    private fun inputProcess() {
        savePasswordBtn.setOnClickListener{
            val currentPassword = currentPasswordBox.editText?.text.toString()
            val newPassword = newPasswordBox.editText?.text.toString()
            val retypePassword = retypePasswordBox.editText?.text.toString()
            val function = "update_password_by_id"
            if(inputValidation(
                    currentPassword,
                    newPassword,
                    retypePassword
                )
            ){
                if(currentPassword == password){
                    RetrofitConnection
                        .getService()
                        .updatePassword(
                            userID,
                            email,
                            newPassword,
                            function
                        )
                        .enqueue(
                            object : Callback<DefaultResponse>{
                                override fun onResponse(
                                    call: Call<DefaultResponse>,
                                    response: Response<DefaultResponse>
                                ) {
                                    changePasswordInSharedPreferences(newPassword)
                                    moveToMainActivity()
                                }

                                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                                    Toast
                                        .makeText(
                                            requireActivity(),
                                            "Gagal upload\nCek Koneksi Internet Kamu",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    Log.d(TAG, "onFailure: ${t.message}"
                                    )
                                    Log.d(
                                        TAG,
                                        "onFailure: \nuser id = " +
                                                "$userID\nemail = " +
                                                "$email\n" +
                                                "new password = $newPassword"
                                    )
                                    Log.d(TAG, "onFailure: ${t.cause}")
                                }

                            }
                        )
                }
                else{
                    Toast
                        .makeText(
                            requireActivity(),
                            "Password Sekarang Salah",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
            else{
                Toast
                    .makeText(
                        requireActivity(),
                        "Lengkapi Password Kamu",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }

    private fun changePasswordInSharedPreferences(newPassword: String) {
        val sharedPreferences = SharedPreferencesData(requireActivity())
        sharedPreferences.save(SHARED_PREFERENCE_PASSWORD_KEY,newPassword)
    }

    private fun moveToMainActivity() {
        Toast
            .makeText(
                requireActivity(),
                "Sukses Mengupdate Password",
                Toast.LENGTH_SHORT
            ).show()
        val intent = Intent(
            requireActivity(),
            MainActivity::class.java
        )
        Thread.sleep(2000)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun inputValidation(
        currentPassword: String,
        newPassword: String,
        retypePassword: String
    ): Boolean {
        if(currentPassword.isEmpty()){
            currentPasswordBox.error = getString(R.string.error_input_password)
            return false
        }
        if(newPassword.isEmpty()){
            newPasswordBox.error = getString(R.string.error_input_password)
            return false
        }
        if(retypePassword.isEmpty()){
            retypePasswordBox.error = getString(R.string.error_input_password)
            return false
        }
        else{
            return true
        }
    }

    private fun bindData() {
        userID = args.userID
        email = args.email
        password = args.oldPassword
        fullname = args.fullname
        picturePath = args.picturePath
    }

    private fun bindingView() {
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
    }
}