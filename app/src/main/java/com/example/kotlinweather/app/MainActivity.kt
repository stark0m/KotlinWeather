package com.example.kotlinweather.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinweather.R
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

        test()

    }
//cu0UCZ5qQJij6wubBVPqmd:APA91bGnZDQPyd-z5JYqxKLL0676pTrWQpMOY_QAib83bcdSR3YT3Z5BV23WxYlDVYcF9t_9dHEZAee6EUEB_Amcwc4j_DR29gxVMbbJJskTirn06bdjeDqScET8FnvHX61TvvV4XVUp
    private fun test() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.i("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("TAG", token)
        })
    }


}