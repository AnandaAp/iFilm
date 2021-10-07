package com.anlian.ifilm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.anlian.ifilm.api.RetrofitConnection
import com.anlian.ifilm.databinding.FragmentSeaderPushNotificationBinding
import com.anlian.ifilm.model.PushNotificationResponse
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.fragment_seader_push_notification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "PUSH NOTIFICATION"
class SeaderPushNotification : Fragment() {
    private lateinit var binding: FragmentSeaderPushNotificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeaderPushNotificationBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        retrieveToken()
        verifyData()
    }

    private fun retrieveToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "Your token : $token"
            Log.d(TAG, msg)
        })
    }

    private fun verifyData() {
        sendNotificationBtn.setOnClickListener {
            val title = titleBox.editText?.text.toString()
            val body = messageBox.editText?.text.toString()
            Log.d(TAG, "title : $title\nbody: $body")
//            if(title.isEmpty()){
//                titleBox.error = getString(R.string.empty_notification)
//            }
//           if(title.isNotEmpty()){
//                titleBox.isErrorEnabled = false
//            }
//            if(body.isEmpty()){
//                messageBox.error = getString(R.string.empty_notification)
//            }
//            if(body.isNotEmpty()){
//                messageBox.isErrorEnabled = false
//            }
            if(title.isNotEmpty() && body.isNotEmpty()){
                Log.d(TAG, "verifyData: masuk untuk mengirim data")
                sendNotification(title, body)
            }
            else{
                Toast
                    .makeText(requireActivity(),
                        getString(R.string.empty_notification),
                        Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendNotification(title: String, body: String) {
        val function = "sendPushNotification"
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d(TAG, "device token: $token")
            RetrofitConnection
                .getService()
                .sendPushNotification(
                    token,
                    title,
                    body,
                    function
                )
                .enqueue(object : Callback<PushNotificationResponse>{
                override fun onResponse(
                    call: Call<PushNotificationResponse>,
                    response: Response<PushNotificationResponse>
                ) {
                    Toast
                        .makeText(requireActivity(),
                            "berhasil mengirim notifikasi",
                            Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<PushNotificationResponse>, t: Throwable) {
                    Toast
                        .makeText(requireActivity(),
                            "gagal mengirim notifikasi",
                            Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "failed with : ${t.message}")
                }

            })
        })
    }
}