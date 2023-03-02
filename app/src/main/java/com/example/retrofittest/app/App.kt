package com.example.retrofittest.app

import android.app.Application
import org.koin.core.context.startKoin

class App : Application() {

    companion object {
        lateinit var instance : App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        startKoin() {

        }
    }
}