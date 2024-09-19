package com.example.e_commercekotlin.presentation.screens


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.handleSearchItem
import com.example.e_commercekotlin.Util.handleToolBarState
import com.example.e_commercekotlin.Util.hide
import com.example.e_commercekotlin.Util.show
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.SharedPreferencesHelper
import com.example.e_commercekotlin.databinding.FragmentFeedBinding
import com.example.e_commercekotlin.Util.setBottomNavVisibility
import com.example.e_commercekotlin.databinding.FragmentMarketBinding
import com.example.e_commercekotlin.presentation.adapter.CategoryAdapter
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.screens.Product_Details.ProductDetailsViewModel
import com.example.e_commercekotlin.presentation.viewmodels.CategoryViewModel
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel

class MarketFragment : Fragment() ,ProductAdapter.ClickListener {

    private lateinit var binding: FragmentMarketBinding
    private var currentFragment: Fragment? = null
    private lateinit var itemAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val productsViewModel: ProductViewModel by viewModels()
    private val productDetailsViewModel: ProductDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.fragmentMarketToolbar.handleToolBarState(
            toolBarTitle = "Market",
            leftIconVisibility = false,
            searchVisibility = false
        )

            switchTab(FeaturedFragment(), binding.tabFeatured)


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.searchBar.setOnClickListener{
            findNavController().navigate(R.id.action_market_fragment_to_searchFragment)
        }

    }
    private fun observeProducts() {
        productsViewModel.data.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.progressBar.show()
                }

                is Resource.Success -> {
                    binding.progressBar.progressBar.hide()
                    resource.data?.let { itemAdapter.setProductList(it) }
                }

                is Resource.Error -> {
                    binding.progressBar.progressBar.hide()
                }
            }
        })
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

    override fun onProductClick(productId: Long, productName: String, productImage: String) {
        // Create a Bundle with the product details
        val bundle = Bundle().apply {
            putInt("productId", productId.toInt())
            putString("product_name", productName)
            putString("product_image", productImage)
            putString("source_fragment", "FeedFragment")

        }

        // Create an instance of CustomDialogFragment and pass the navigation action as a lambda
        val dialogFragment = CustomDialogFragment {
            // Action to be executed when the user clicks in the dialog
            val action = FeedFragmentDirections.actionFeedFragmentToProductDetails(productId.toInt())
            findNavController().navigate(action)
        }

        // Set the arguments (product details) for the dialog
        dialogFragment.arguments = bundle

        // Show the dialog
        dialogFragment.show(parentFragmentManager, "CustomDialogFragment")
    }
    }

