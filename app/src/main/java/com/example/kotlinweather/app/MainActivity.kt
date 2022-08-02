package com.example.kotlinweather.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinweather.R
import com.example.kotlinweather.domain.StactiFun
import com.example.kotlinweather.view.weathershow.WeatherShowFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WeatherShowFragment.newInstance()).commit()
        }
//        StactiFun.makePushNotification(this,"title","mainactivity")

    }
//cu0UCZ5qQJij6wubBVPqmd:APA91bGnZDQPyd-z5JYqxKLL0676pTrWQpMOY_QAib83bcdSR3YT3Z5BV23WxYlDVYcF9t_9dHEZAee6EUEB_Amcwc4j_DR29gxVMbbJJskTirn06bdjeDqScET8FnvHX61TvvV4XVUp
//    AAAAF3WZ82Y:APA91bH4Gz5XxTewP9Xm2q3mekOXjlvYUJfTLmiMZjnnCjWwLMCCs-OdXssrFqHq7ekUnFTAiW7cVkRP6lf6ll0SypwLIIsYOZpdEFXV61T02oXOGucRot2k5lQOMEEdBhhgRXGrbx_e


}