package com.example.kotlinweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        findViewById<MaterialButton>(R.id.mainactivity_click_me_button).setOnClickListener() {

            doAction()
            logCycles()
        }
    }

    private fun logCycles() {
        val week = listOf(
            "Понедельник",
            "Вторник",
            "Среда",
            "Четверг",
            "Пятница",
            "Суббота",
            "Воскресенье"
        )
        Log.d(TAG, "=================")
        week.forEach {
            Log.d(TAG, "$it")
        }
        Log.d(TAG, "=================")
        repeat(week.size) {
            Log.d(TAG, "${week[it]}")
        }
        Log.d(TAG, "=================")
        for (i in week) {
            Log.d(TAG, "$i ")
        }
        Log.d(TAG, "=================")
        for (i in week.indices) {
            Log.d(TAG, "${week[i]}")
        }

        Log.d(TAG, "=================")
        for (i in 0..week.size - 1) {
            Log.d(TAG, "${week[i]}")
        }
        Log.d(TAG, "=================")
        for (i in 0 until week.size) {
            Log.d(TAG, "${week[i]}")
        }

        Log.d(TAG, "=================")
        for (i in week.size - 1 downTo 0) {
            Log.d(TAG, "${week[i]}")
        }



    }


    fun localToast(text: String) {
        Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
    }

    private fun doAction() {
        val andrey = Person("Andrey", 45)
        localToast("${andrey.name} весит ${andrey.weight}")
        val obj = object {
            val clone = andrey.copy(weight = 49)
        }
        localToast("появился клон по имени ${obj.clone.name} и весом ${obj.clone.weight}")
    }

    companion object{
        const val TAG = "LOGTAG"
    }
}