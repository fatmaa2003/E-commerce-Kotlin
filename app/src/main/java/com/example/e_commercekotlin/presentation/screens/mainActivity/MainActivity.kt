package com.example.e_commercekotlin.presentation.screens.mainActivity

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.SharedPreferencesHelper
import com.example.e_commercekotlin.databinding.ActivityMainBinding
import com.example.e_commercekotlin.presentation.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            // Dark mode is active, change status bar to dark mode color
            setStatusBarColor(R.color.black) // Replace with your desired color
        } else {
            // Light mode is active, change status bar to light mode color
            setStatusBarColor(R.color.white) // Replace with your desired color
        }

        // Safely get NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController ?: run {
            Log.e("MainActivity", "NavHostFragment not found or NavController is null")
            return
        }

        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(R.navigation.my_nav)

        val isLoggedIn = checkIfUserIsLoggedIn()
        Log.d("TAG", "isLoggedIn: $isLoggedIn")

        // Set the start destination based on login state
        if (isLoggedIn) {
            navGraph.setStartDestination(R.id.Feed_fragment)
        } else {
            navGraph.setStartDestination(R.id.sign_in)
        }

        // Set the graph on the NavController after modifying it
        navController.graph = navGraph

        val bottomNavView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(bottomNavView, navController)

        // Set up back navigation handling
        setupBackNavigationHandler(navController)
    }

    private fun setupBackNavigationHandler(navController: androidx.navigation.NavController) {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!navController.navigateUp()) {
                    // If no more fragments in the back stack, finish the activity
                    finish()
                }
            }
        })
    }

    private fun checkIfUserIsLoggedIn(): Boolean = SharedPreferencesHelper.getToken() != null

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val navController = navHostFragment?.navController ?: return super.onSupportNavigateUp()
        return navController.navigateUp() || super.onSupportNavigateUp()
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
