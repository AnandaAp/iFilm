package com.anlian.ifilm

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.anlian.ifilm.api.RetrofitConnection
import com.anlian.ifilm.controller.SharedPreferencesData
import com.anlian.ifilm.databinding.FragmentLoginPageBinding
import com.anlian.ifilm.model.DefaultResponse
import com.anlian.ifilm.model.ProfileResponse
import kotlinx.android.synthetic.main.fragment_login_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


//const val REQUEST_CODE = "AUTH 313"
class LoginPage : Fragment() {
    private lateinit var binding: FragmentLoginPageBinding
    private lateinit var email:String
    private lateinit var password:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hardwarePreferences = SharedPreferencesData.HardwarePreferences(requireActivity())
        val hardwareID = hardwarePreferences
            .getValueString(SharedPreferencesData.SHARED_PREFERENCE_HARDWARE_KEY)
        signInProcess()
        if(checkFingerPrintStatus()){
            if(hardwareID.isNotEmpty()) {
                orTxt.visibility = View.VISIBLE
                fingerPrintInfo.visibility = View.VISIBLE
                binding.fingePrintBtn.visibility = View.VISIBLE
                displayFingerprint()
            }
        }
    }

    private fun displayFingerprint() {
        val executor = ContextCompat.getMainExecutor(requireActivity())
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(requireActivity(),
                        "Authentication error: $errString", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(requireActivity(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(requireActivity(), "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for iFilm")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.
        binding.fingePrintBtn.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun checkFingerPrintStatus(): Boolean {
        val biometricManager = BiometricManager.from(requireActivity())
        var value = false
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.")
                value = true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.e("MY_APP_TAG", "No biometric features available on this device.")
                value = false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.")
                value = false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                            BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                    }
                    Log.d(TAG, "checkFingerPrintStatus: finger print tidak terseting")
                    value = false
                } else {
                    value = false
                }
            }
        }
        return value
    }

    private fun signInProcess() {
        binding.signInBtnConfirm.setOnClickListener{
            getInputData() // digunakan untuk mengambil input dari edit text box
        }
    }

    private fun getInputData() {
        email = binding.emailSignInBoxField.editText?.text.toString().trim()
        password = binding.passwordSignInBoxField.editText?.text.toString().trim()
        if(email.isEmpty() || password.isEmpty()) {
            if(email.isEmpty()){
                binding.emailSignInBoxField.error = getString(R.string.empty_input)
            }
            if(password.isEmpty()){
                binding.passwordSignInBoxField.error = getString(R.string.empty_input)
            }
            Toast
                .makeText(
                    requireActivity(),
                    getString(R.string.empty_input),
                    Toast.LENGTH_SHORT)
                .show()
            return
        }
        else{
            getUserData()
        }
    }

    @SuppressLint("HardwareIds")
    fun getUserData() {
        val function = "sign_in"
        val hardwareID = Settings
            .Secure
            .getString(requireActivity()
                .contentResolver,
                Settings
                    .Secure
                    .ANDROID_ID)
        RetrofitConnection
            .getService()
            .signIn(function,email,password)
            .enqueue(object : Callback<ProfileResponse>{
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    if (response.isSuccessful){
                        saveToSharedPreferences(response)
                         uploadHardwareID(email,hardwareID)
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

    private fun uploadHardwareID(email: String, hardwareID: String) {
        val function = "signInAddHardwareID"
        RetrofitConnection
            .getService()
            .signInAddHardwareID(email,hardwareID,function)
            .enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Log.d(TAG, "hardware id: $hardwareID")
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure: gagal\nhardware id = $hardwareID")
                }
            })
    }

    private fun saveToSharedPreferences(response: Response<ProfileResponse>) {
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
        val hardwareID = response
            .body()
            ?.profile
            ?.get(0)
            ?.hardwareID
        Toast
            .makeText(
                requireActivity(),
                getString(R.string.sign_in_success),
                Toast.LENGTH_LONG)
            .show()
        saveLoginSession(userID!!,fullname!!,email!!, pass!!,picturePath!!,hardwareID!!)
    }

    private fun saveLoginSession(
        userID: String,
        fullname: String,
        email: String,
        pass: String,
        picturePath: String,
        hardwareID: String
    ) {
        val sharedPreferences = SharedPreferencesData(requireActivity())
        val hardwarePreferences = SharedPreferencesData.HardwarePreferences(requireActivity())
        sharedPreferences
            .save(
                SharedPreferencesData
                    .SHARED_PREFERENCE_ID_KEY,
                userID
            )
        sharedPreferences
            .save(
                SharedPreferencesData.SHARED_PREFERENCE_EMAIL_KEY,
                email
            )
        sharedPreferences
            .save(
                SharedPreferencesData.SHARED_PREFERENCE_PASSWORD_KEY,
                pass
            )
        sharedPreferences
            .save(
                SharedPreferencesData.SHARED_PREFERENCE_FULLNAME_KEY,
                fullname
            )
        sharedPreferences
            .save(
                SharedPreferencesData.SHARED_PREFERENCE_PICTURE_KEY,
                picturePath
            )
        sharedPreferences
            .save(
                SharedPreferencesData
                    .SHARED_PREFERENCE_SESSION_KEY,
                true
            )
        sharedPreferences
            .save(
                SharedPreferencesData.SHARED_PREFERENCE_HARDWARE_KEY,
                hardwareID
            )
        hardwarePreferences
            .save(
                SharedPreferencesData.SHARED_PREFERENCE_HARDWARE_KEY,
                hardwareID
            )
        navigateToHomePage()
    }

    private fun navigateToHomePage() {
        Thread.sleep(1000)
        val intent = Intent(
            requireActivity(),
            MainActivity::class.java
        )
        startActivity(intent)
        requireActivity().finish()
    }

    private fun bindingView() {
        binding = FragmentLoginPageBinding.inflate(layoutInflater)
    }
}