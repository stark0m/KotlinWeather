package com.example.kotlinweather.domain

import android.app.AlertDialog
import android.content.Context



object StactiFun{
    fun infrormCustomerAboutDeclineGPRSAccess(context:Context)  {
        AlertDialog.Builder(context)
            .setTitle("Отсутствует доступ к гео данным, включите геоданные")
            .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }.show()
    }
}
