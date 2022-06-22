package com.example.kotlinweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        findViewById<MaterialButton>(R.id.mainactivity_click_me_button).setOnClickListener(){

            doAction()
        }
    }


    fun localToast(text:String){
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }
    private fun doAction() {
        val andrey = Person("Andrey",45)
        localToast("${andrey.name} весит ${andrey.weight}")
        val obj = object {
            val clone= andrey.copy(weight = 49)
              }
        localToast("появился клон по имени ${obj.clone.name} и весом ${obj.clone.weight}")
    }
}