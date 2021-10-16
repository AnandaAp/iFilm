package com.anlian.ifilm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anlian.ifilm.architecture.MainView
import com.anlian.ifilm.architecture.HomePresenter
import com.anlian.ifilm.controller.Adapter
import com.anlian.ifilm.controller.SharedPreferencesData
import com.anlian.ifilm.databinding.FragmentHomeBinding
import com.anlian.ifilm.model.DataItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.messaging.FirebaseMessaging

class Home : Fragment(), MainView {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: Adapter
    private lateinit var userID: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var fullname: String
    private lateinit var picturePath: String
    private lateinit var token: String
    private lateinit var hardwareID: String
    private var isLogin: Boolean = false
    private val BASE_URL = "http://192.168.1.8/ilist/profiles/pictures/"
    private val TAG = "HOME"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkSessions()
        if (checkGooglePlayServices()) {
            token = FirebaseMessaging
                .getInstance()
                .token
                .toString()

        } else {
            //You won't be able to send notifications to this device
            Log.w(TAG, "Device doesn't have google play services")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        initBinding()
        Log.d(TAG, "sudah login: $isLogin")
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginAuth()
        HomePresenter(this).getData()
        moveToProfile()
        moveToSead()
    }

    override fun moveToSead() {
        binding.addNotificationBtn.setOnClickListener{
            findNavController()
                .navigate(R.id.action_home2_to_seaderPushNotification)
        }
    }

    override fun resultSuccess(result: List<DataItem?>?) {
        adapterStart()
        showData(result)
    }

    override fun resultFailed(throwable: Throwable) {
        Log.d(TAG, "resultFailed: data tidak berhasil di ambil")
    }

    private fun loginAuth() {
        if(isLogin){
            Glide
                .with(requireActivity())
                .load("$BASE_URL$picturePath")
                .apply(RequestOptions().override(24, 32))
                .into(binding.profileBtnImg)
        }
    }

    private fun checkSessions() {
        val preferences = SharedPreferencesData(requireActivity())
        isLogin = preferences
            .getValueBoolean(
                SharedPreferencesData.SHARED_PREFERENCE_SESSION_KEY
            )
        if(isLogin){
            userID = preferences
                .getValueString(SharedPreferencesData.SHARED_PREFERENCE_ID_KEY)
            email = preferences
                .getValueString(SharedPreferencesData.SHARED_PREFERENCE_EMAIL_KEY)
            password = preferences
                .getValueString(SharedPreferencesData.SHARED_PREFERENCE_PASSWORD_KEY)
            fullname = preferences
                .getValueString(SharedPreferencesData.SHARED_PREFERENCE_FULLNAME_KEY)
            picturePath = preferences
                .getValueString(SharedPreferencesData.SHARED_PREFERENCE_PICTURE_KEY)
            hardwareID = preferences
                .getValueString(SharedPreferencesData.SHARED_PREFERENCE_HARDWARE_KEY)
        }
    }

    override fun moveToProfile() {
        when(isLogin){
            true -> {
                binding.profileBtn.setOnClickListener{
                    val direction = HomeDirections.actionHome2ToProfileDetail(
                        userID,
                        email,
                        password,
                        fullname,
                        picturePath,
                        hardwareID
                    )
                    findNavController().navigate(direction)
                }
            }
            false -> {
                binding.profileBtn.setOnClickListener{
                    val direction = HomeDirections.actionHome2ToRegisterPage()
                    findNavController().navigate(direction)
                }
            }
        }
    }

    private fun initBinding() {
        binding = FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun adapterStart() {
        adapter = Adapter(requireActivity(), arrayListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapter
        insertData()
    }

    override fun showData(result: List<DataItem?>?) {
        updateAdapter(result)
    }

    override fun updateAdapter(result: List<DataItem?>?) {
        adapter.setData(result as ArrayList<DataItem>)
    }

    private fun checkGooglePlayServices(): Boolean {
        // 1
        val status = GoogleApiAvailability
            .getInstance()
            .isGooglePlayServicesAvailable(requireActivity())
        // 2
        return if (status != ConnectionResult.SUCCESS) {
            Log.e(TAG, "Error")
            // ask user to update google play services and manage the error.
            false
        } else {
            // 3
            Log.i(TAG, "Google play services updated")
            true
        }
    }

    override fun insertData() {
        binding.addMovieBtn.setOnClickListener{
            findNavController().navigate(R.id.action_home2_to_insert)
        }
    }
}