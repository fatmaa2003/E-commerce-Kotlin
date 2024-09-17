package com.example.e_commercekotlin.presentation.screens


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.handleToolBarState
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
        binding.fragmentMarketToolbar.handleToolBarState(
            leftIconImage = R.drawable.disk,
            searchVisibility = false
        )
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

        binding.tabStores.setOnClickListener {
            if (currentFragment !is BrandFragment) {
                switchTab(BrandFragment(), binding.tabStores)
            }
        }

        binding.tabTags.setOnClickListener {
            if (currentFragment !is FollowFragment) {
                switchTab(TagsFragment(), binding.tabTags)
            }
        }

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
        val dotViews = listOf(binding.dotFeatured, binding.dotCollection,binding.dotStores,binding.dotTags)

        tabViews.forEachIndexed { index, tabView ->
            val textView = tabView as? TextView

            if (textView != null) {
                textView.setTextColor(
                    if (tabView == selectedTab) {
                        dotViews.getOrNull(index)?.visibility = View.VISIBLE
                        ContextCompat.getColor(requireContext(), R.color.chips_color)
                    } else {
                        dotViews.getOrNull(index)?.visibility = View.GONE
                        ContextCompat.getColor(requireContext(), R.color.lightgray)
                    }
                )
            }
        }
    }
}
