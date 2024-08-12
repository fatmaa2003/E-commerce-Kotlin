package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.model.UserRole

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userRole = intent.getStringExtra("USER_ROLE")?.let { UserRole.valueOf(it) }

        val welcomeMessage = findViewById<TextView>(R.id.welcomeMessage)
        welcomeMessage.text = when (userRole) {
            UserRole.ADMIN -> "Hi Admin"
            UserRole.USER -> "Hi User"
            else -> "Hi"
        }
    }
}
