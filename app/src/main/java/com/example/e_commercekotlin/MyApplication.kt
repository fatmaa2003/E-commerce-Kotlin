package com.example.e_commercekotlin

import android.app.Application
import android.content.pm.ActivityInfo
import com.example.e_commercekotlin.data.SharedPreferencesHelper
import com.example.e_commercekotlin.data.local.DatabaseBuilder

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesHelper.init(this)
        DatabaseBuilder.getInstance(this)

    }
}