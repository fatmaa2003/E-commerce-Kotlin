package com.example.e_commercekotlin.presentation.screens.mainActivity

import android.os.Bundle
import android.util.Log
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
}
