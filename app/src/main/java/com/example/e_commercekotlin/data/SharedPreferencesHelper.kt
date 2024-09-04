package com.example.e_commercekotlin.data

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesHelper {

    private lateinit var sharedPreferences: SharedPreferences

    private const val MY_TOKEN = "MY_TOKEN"

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(MY_TOKEN, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(MY_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(MY_TOKEN, null)
    }


    fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    fun removeToken() {
        sharedPreferences.edit().remove(MY_TOKEN).apply()
    }
}