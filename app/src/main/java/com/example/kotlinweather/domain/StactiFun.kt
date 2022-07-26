package com.example.kotlinweather.domain

import android.app.AlertDialog
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.kotlinweather.R
import com.example.kotlinweather.app.MainActivity


object StactiFun{
    fun infrormCustomerAboutDeclineGPRSAccess(context:Context)  {
        AlertDialog.Builder(context)
            .setTitle("Отсутствует доступ к гео данным, включите геоданные")
            .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }.show()
    }

    fun makePushNotification(context: Context,title:String?,body:String?){
        val notificationManager = NotificationManagerCompat.from(context)
        createChannelsAndNotification(notificationManager,"CHANNEL","канал сообщений приложения")
        notificationManager.notify(NOTIFICATION_ID, createNotification(context,title?:"empty",body?:"empty body"))
    }
    private fun createChannelsAndNotification(notificationManager: NotificationManagerCompat, channelName:String = "Weather channel", description:String) {
        val channel = NotificationChannelCompat.Builder(
            CHANNEL_ID_HIGHT,
            NotificationManagerCompat.IMPORTANCE_HIGH
        )
            .setName(channelName)
            .setDescription(description)
            .build()
        notificationManager.createNotificationChannel(channel)
    }
    private fun createNotification(context: Context,title:String,text:String): Notification {
        val icon = R.drawable.ic_appicon

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(context, CHANNEL_ID_HIGHT)
            .setContentTitle(title)
            .setSmallIcon(icon)
            .setContentText(text)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_appicon)
            .build()
    }

}
