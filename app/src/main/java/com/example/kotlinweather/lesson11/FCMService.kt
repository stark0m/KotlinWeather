package com.example.kotlinweather.lesson11


import com.example.kotlinweather.domain.*
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService() {


    override fun onMessageReceived(message: RemoteMessage) {

        val data = message.data
        val title = data[NOTIFICATION_KEY_TITLE]
        val body = data[NOTIFICATION_KEY_BODY]
        StactiFun.makePushNotification(this,title,body)
        super.onMessageReceived(message)


    }


}