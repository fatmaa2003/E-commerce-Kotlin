package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.handleToolBarState
import com.example.e_commercekotlin.data.model.Product
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.adapter.ViewPagerAdapter
import com.example.e_commercekotlin.databinding.FragmentStoreDetailsBinding

class StoreDetailsFragment : Fragment() {

    private lateinit var productsOnSaleAdapter: ProductAdapter
    private lateinit var summerSpringAdapter: ProductAdapter
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var _binding: FragmentStoreDetailsBinding? = null
    private val binding get() = _binding!!
    private var isFollowing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Handle toolbar state here after binding is initialized
        binding.fragmentStoreDetails.handleToolBarState(
            rightIconImage = R.drawable.three_dots
        )

        productsOnSaleAdapter = ProductAdapter()
        summerSpringAdapter = ProductAdapter()

        binding.rvproductsonsale.adapter = productsOnSaleAdapter
        binding.rvproductsonsale.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.rvsummerspring.adapter = summerSpringAdapter
        binding.rvsummerspring.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val imageList = listOf(
            "https://i.imgur.com/MxJyADq.jpeg",
            "https://i.imgur.com/MxJyADq.jpeg",
            "https://i.imgur.com/MxJyADq.jpeg"
        )

        viewPagerAdapter = ViewPagerAdapter(imageList)
        binding.viewPager2.adapter = viewPagerAdapter
        binding.viewPager2.setPageTransformer { page, position ->
            page.alpha = 1 - Math.abs(position)
        }

        binding.tabOverview.setOnClickListener { highlightTab(binding.tabOverview) }
        binding.tabCollection.setOnClickListener { highlightTab(binding.tabCollection) }
        binding.tabBlog.setOnClickListener { highlightTab(binding.tabBlog) }

        updateButtonState()

        binding.followButton.root?.setOnClickListener {
            isFollowing = !isFollowing
            updateButtonState()
        }

        loadProducts()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun highlightTab(selectedTab: View) {
        val tabViews = listOf(binding.tabOverview, binding.tabCollection, binding.tabBlog)
        val dotViews = listOf(binding.dotOverview, binding.dotCollection, binding.dotBlog)

        tabViews.forEachIndexed { index, tabView ->
            val textView = tabView as? TextView

            textView?.setTextColor(
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

    private fun updateButtonState() {
        val buttonText = binding.followButton.buttonTv
        buttonText?.text = if (isFollowing) "Following" else "Follow"
        val buttonTextColor = if (isFollowing) getResources().getColor(R.color.black) else getResources().getColor(R.color.white)
        val buttonBackground = if (isFollowing) R.drawable.following else R.drawable.follow
        binding.followButton.root.setBackgroundResource(buttonBackground)
        binding.followButton.buttonTv.setTextColor(buttonTextColor)
    }
}
