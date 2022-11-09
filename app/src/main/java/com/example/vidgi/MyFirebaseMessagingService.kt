package com.example.vidgi

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    val TAG = "firebase"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New Token: ${token}")
        Prefs.setString("FB_TOKEN", token!!)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "onMessageReceived")

        if (message?.notification != null) {
            val title = message?.notification?.title
            val body = message?.notification?.body

            NotificationUtil.create(1, Intent(this, HomeActivity::class.java), "${title}", "${body}")
        }
    }
}