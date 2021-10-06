package com.anlian.ifilm

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

const val topic = "greeting"
class MainActivity : AppCompatActivity() {
//    private var backPressedTime:Long = 0
//    lateinit var backToast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeTopic()
        setContentView(R.layout.activity_main)
    }

    private fun subscribeTopic() {
        Firebase.messaging.subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                var msg = getString(R.string.msg_subscribed)
                if (!task.isSuccessful) {
                    msg = getString(R.string.msg_subscribe_failed)
                }
                Log.d(TAG, msg)
            }
    }

//    override fun onBackPressed() {
//        backToast = Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG)
//        if (backPressedTime + 2000 > System.currentTimeMillis()) {
//            backToast.cancel()
//            super.onBackPressed()
//            finish()
//        } else {
//            backToast.show()
//        }
//        backPressedTime = System.currentTimeMillis()
//    }
}