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

        binding.tabFeatured.setOnClickListener { switchTab(binding.tabFeatured, binding.tabFeatured) }
        binding.tabCollection.setOnClickListener { switchTab(binding.tabCollection, binding.tabCollection) }
        binding.tabStores.setOnClickListener { switchTab(binding.tabStores, binding.tabStores) }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    private fun switchTab(contentToShow: View, tabToHighlight: View) {
//
//        listOf(binding.tabFeatured, binding.tabCollection, binding.tabStores, binding.tabTags).forEach { contentView ->
//            contentView.visibility = if (contentView == contentToShow) View.VISIBLE else View.GONE
//        }


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
