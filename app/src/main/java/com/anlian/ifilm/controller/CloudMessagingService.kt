package com.anlian.ifilm.controller

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_DEFAULT
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import com.anlian.ifilm.MainActivity
import com.anlian.ifilm.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "313"
const val channelName = "iFilm_notification"
class CloudMessagingService: FirebaseMessagingService() {
    private val TAG = "FIREBASE MESSAGE"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
//        if(remoteMessage.notification != null){
//            Log.d(TAG, "ada notifikasi")
//            val title = remoteMessage.notification!!.title.toString()
//            val body = remoteMessage.notification!!.body.toString()
//            Log.d(TAG, "title: $title")
//            Log.d(TAG, "body: $body")
//            showNotification(title, body)
//            generateMessage(remoteMessage
//                .notification!!
//                .title!!,
//                remoteMessage
//                    .notification!!
//                    .body!!)
//        }
//        // Check if message contains a data payload.
//        if (remoteMessage.data.isNotEmpty()) {
//            Log.d(TAG, "ada data payload")
//            val title = remoteMessage.notification!!.title.toString()
//            val body = remoteMessage.notification!!.body.toString()
//            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
//            showNotification(title,body)
//        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            showNotification(it.title.toString(),it.body.toString())
//            generateMessage(it.title.toString(),it.body.toString())
        }
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private fun showNotification(title: String, message: String){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingActivity = PendingIntent.getActivity(this,
            323,
            intent,
            FLAG_ONE_SHOT)
        val builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_icon)
            .setAutoCancel(true)
            .setSilent(false)
            .setVisibility(VISIBILITY_PUBLIC)
            .setContentIntent(pendingActivity)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationCannel = NotificationChannel(channelId,channelName,IMPORTANCE_DEFAULT)
            notificationCannel.description = message
            notificationManager.createNotificationChannel(notificationCannel)
        }
        notificationManager.notify(314, builder.build())
//        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
//            notify(313, builder.build())
//        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

    }
}