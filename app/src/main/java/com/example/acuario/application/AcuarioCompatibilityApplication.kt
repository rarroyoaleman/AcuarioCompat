package com.ralemancode.acuario

import android.app.Application
import com.example.acuario.providers.firebase.FirebaseProvider.Companion.createNotificationChannel
import com.example.acuario.providers.firebase.FirebaseProvider.Companion.recoveryRegistryToken

class acuarioApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        createNotificationChannel(this)
        recoveryRegistryToken(this)
    }











}