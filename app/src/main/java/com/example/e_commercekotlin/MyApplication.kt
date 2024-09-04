package com.example.e_commercekotlin

import android.app.Application
import com.example.e_commercekotlin.data.SharedPreferencesHelper

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SharedPreferencesHelper.init(this)
    }
}