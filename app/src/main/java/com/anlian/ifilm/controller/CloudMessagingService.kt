package com.anlian.ifilm.controller

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.anlian.ifilm.MainActivity
import com.anlian.ifilm.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "313"
const val channelName = "iFilm_notification"
const val packageApplicationName = "com.anlian.ifilm"
class CloudMessagingService: FirebaseMessagingService() {
    private val TAG = "FIREBASE MESSAGE"

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun generateMessage(title:String, message:String){
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingActivity = PendingIntent.getActivity(this, 0, intent,FLAG_ONE_SHOT)
        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_icon)
            .setAutoCancel(true)
            .setContentIntent(pendingActivity)
        builder.setContent(getRemoteView(title,message))
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationCannel = NotificationChannel(channelId,channelName,IMPORTANCE_HIGH)
            notificationCannel.enableLights(true)
            notificationCannel.lightColor = Color.RED
            notificationCannel.enableVibration(true)
            notificationCannel.description = message
            notificationManager.createNotificationChannel(notificationCannel)
        }
        notificationManager.notify(0,builder.build())
    }

    @SuppressLint("RemoteViewLayout")
    private fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteViews = RemoteViews(packageApplicationName, R.layout.notification)
        remoteViews.setTextViewText(R.id.notificationAppTitle,title)
        remoteViews.setTextViewText(R.id.notificationTextTitle,message)
        remoteViews.setImageViewResource(R.id.notificationSmallImage,R.drawable.ic_icon)
        return remoteViews
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

    }
//
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if(remoteMessage.notification != null){
            generateMessage(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }
//
//    private fun sendRegistrationToServer(token: String) {
//        Log.d(TAG, "sendRegistrationToServer: $token")
//    }
}