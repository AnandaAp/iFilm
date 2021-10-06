package com.anlian.ifilm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.anlian.ifilm.databinding.FragmentSeaderPushNotificationBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import kotlinx.android.synthetic.main.fragment_seader_push_notification.*

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
//            Toast
//                .makeText(requireActivity(),
//                    msg,
//                    Toast.LENGTH_SHORT).show()
        })
    }

    private fun verifyData() {
        sendNotificationBtn.setOnClickListener {
            val title = titleBox.editText?.text.toString()
            val body = messageBox.editText?.text.toString()
            if(title.isEmpty()){
                titleBox.error = getString(R.string.empty_notification)
            }
            else if(title.isNotEmpty()){
                titleBox.isErrorEnabled = false
            }
            if(body.isEmpty()){
                messageBox.error = getString(R.string.empty_notification)
            }
            else if(body.isNotEmpty()){
                messageBox.isErrorEnabled = false
            }
            else{
                sendNotification()
            }
        }
    }

    private fun sendNotification() {

    }
}