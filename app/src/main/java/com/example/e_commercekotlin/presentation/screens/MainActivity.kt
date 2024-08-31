package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.databinding.ActivityMainBinding
import com.example.e_commercekotlin.presentation.viewmodels.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set initial fragment
        replaceFragment(FeedFragment())

        // Observe data from ViewModel
        observeData()

        // Setup bottom navigation
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Feed -> replaceFragment(StoreDetailsFragment())
                R.id.Market -> replaceFragment(ProductDetailsFragment())
                R.id.Profile -> replaceFragment(PaymentFragment())
            }
            true
        }
    }

    private fun observeData() {
        viewModel.data.observe(this, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Handle loading state if needed
                }
                is Resource.Success -> {
                    // Handle success state if needed
                }
                is Resource.Error -> {
                    val errorMessage = resource.message ?: "An unexpected error occurred"
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }

                is Resource.Error -> TODO()
                is Resource.Loading -> TODO()
                is Resource.Success -> TODO()
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}
