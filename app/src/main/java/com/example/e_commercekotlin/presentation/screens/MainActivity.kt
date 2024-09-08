package com.example.e_commercekotlin.presentation.screens

import CartFragment
import DressesDetails
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.ActivityMainBinding
import com.example.e_commercekotlin.presentation.ui.StoreDetailsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(FeedFragment())
        setSupportActionBar(binding.toolbar.root)
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Feed -> replaceFragment(FeedFragment())
                R.id.Market -> replaceFragment(MarketFragment())
                R.id.Profile -> replaceFragment(CartFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}
