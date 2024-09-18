package com.example.e_commercekotlin.presentation.screens

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.presentation.screens.mainActivity.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread


class SplashScreenActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            // Dark mode is active, change status bar to dark mode color
            setStatusBarColor(R.color.black) // Replace with your desired color
        } else {
            // Light mode is active, change status bar to light mode color
            setStatusBarColor(R.color.white) // Replace with your desired color
        }
        lifecycleScope.launch {
            delay(2500)
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()

        }
    }

    private fun setStatusBarColor(colorResource: Int) {
        window.statusBarColor = resources.getColor(colorResource, theme)

        // Set status bar text color depending on the mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController!!.setSystemBarsAppearance(
                if (colorResource == android.R.color.white) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val decor = window.decorView
                if (colorResource == android.R.color.white) {
                    decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    decor.systemUiVisibility = 0
                }
            }
        }
    }
}