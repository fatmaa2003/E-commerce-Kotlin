package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.FragmentMarketBinding

class MarketFragment : Fragment() {

    private lateinit var binding: FragmentMarketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketBinding.inflate(inflater, container, false)
        val view = binding.root


        showFragment(FeaturedFragment())


        binding.tabFeatured.setOnClickListener { switchTab(FeaturedFragment(), binding.tabFeatured) }


        return view
    }

    private fun showFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun switchTab(fragment: Fragment, tabToHighlight: View) {

        showFragment(fragment)


        listOf(binding.tabFeatured, binding.tabCollection, binding.tabStores, binding.tabTags).forEach { tabView ->
            tabView.setTextColor(
                if (tabView == tabToHighlight) {
                    ContextCompat.getColor(requireContext(), R.color.black)
                } else {
                    ContextCompat.getColor(requireContext(), R.color.lightgray)
                }
            )
        }
    }
}
