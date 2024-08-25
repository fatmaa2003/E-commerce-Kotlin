package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.FragmentMarketBinding

class MarketFragment : Fragment() {

    private lateinit var binding: FragmentMarketBinding
    private var currentFragment: Fragment? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketBinding.inflate(inflater, container, false)
        val view = binding.root

        if (savedInstanceState == null) {
            switchTab(FeaturedFragment(), binding.tabFeatured)
        }

        binding.tabFeatured.setOnClickListener {
            if (currentFragment !is FeaturedFragment) {
                switchTab(FeaturedFragment(), binding.tabFeatured)
            }
        }

        binding.tabCollection.setOnClickListener {
            if (currentFragment !is CollectionFragment) {
                switchTab(CollectionFragment(), binding.tabCollection)
            }
        }

        // Ensure to set listeners for other tabs if needed
        // binding.tabStores.setOnClickListener { ... }
        // binding.tabTags.setOnClickListener { ... }

        return view
    }

    private fun showFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
        currentFragment = fragment
    }

    private fun switchTab(fragment: Fragment, tabToHighlight: View) {
        showFragment(fragment)
        highlightTab(tabToHighlight)
    }

    private fun highlightTab(selectedTab: View) {
        val tabViews = listOf(binding.tabFeatured, binding.tabCollection, binding.tabStores, binding.tabTags)
        val dotViews = listOf(binding.dotFeatured, binding.dotCollection)

        tabViews.forEachIndexed { index, tabView ->
            val textView = tabView as? TextView

            if (textView != null) {
                textView.setTextColor(
                    if (tabView == selectedTab) {
                        dotViews.getOrNull(index)?.visibility = View.VISIBLE
                        ContextCompat.getColor(requireContext(), R.color.black)
                    } else {
                        dotViews.getOrNull(index)?.visibility = View.GONE
                        ContextCompat.getColor(requireContext(), R.color.lightgray)
                    }
                )
            }
        }
    }
}
