package com.example.vidgi

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationUtil {
    internal val CHANEL_ID = "1"

    fun createChanel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val context = VidgiApplication.getInstance()
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val appName = context.getString(R.string.app_name)
            val content = NotificationChannel(CHANEL_ID, appName, NotificationManager.IMPORTANCE_HIGH)

            manager.createNotificationChannel(content)
        }
    }


    fun  create(id: Int, intent: Intent, title: String, text: String) {
        val context = VidgiApplication.getInstance().applicationContext

        createChanel()

        val content = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, CHANEL_ID)
            .setContentIntent(content)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

        with(NotificationManagerCompat.from(VidgiApplication.getInstance())) {
            notify(id, builder.build())
        }

    }
}