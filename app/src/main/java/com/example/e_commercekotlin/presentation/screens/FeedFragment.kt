package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.handleSearchItem
import com.example.e_commercekotlin.Util.handleToolBarState
import com.example.e_commercekotlin.Util.setBottomNavVisibility
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.SharedPreferencesHelper
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.databinding.FragmentFeedBinding
import com.example.e_commercekotlin.presentation.adapter.CategoryAdapter
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.screens.Product_Details.ProductDetailsViewModel
import com.example.e_commercekotlin.presentation.viewmodels.CategoryViewModel
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel
import kotlinx.coroutines.launch

class FeedFragment : Fragment(), ProductAdapter.ClickListener {
    private lateinit var itemAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val productsViewModel: ProductViewModel by viewModels()
    private val productDetailsViewModel: ProductDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        observeProducts()
        binding.feedFragmentToolBar.handleToolBarState(
            toolBarTitle = "Feed", leftIconImage = R.drawable.cartt, rightIconImage = R.drawable.logout_icon,
            rightIconVisibility = true
        )
        activity?.setBottomNavVisibility(visible = true)

        handleSearchItem(binding = binding.feedFragmentToolBar, action = R.id.action_Feed_fragment_to_searchFragmente, fragment = this)
       binding.feedFragmentToolBar.placeHolderIcon.setOnClickListener{
           SharedPreferencesHelper.removeToken()

           findNavController().navigate(R.id.action_Feed_fragment_to_sign_in)
       }
        binding.feedFragmentToolBar.leftIcon.setOnClickListener {
            findNavController().navigate(R.id.action_Feed_fragment_to_cart)
        }


        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.categoriesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        itemAdapter = ProductAdapter()
        categoryAdapter = CategoryAdapter()
        itemAdapter.setListener(this)

        binding.recyclerView.adapter = itemAdapter
        binding.categoriesRecyclerView.adapter = categoryAdapter

        onCategoryClick()
    }


    private fun observeProducts() {
        productsViewModel.data.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { itemAdapter.setProductList(it) }
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun observeData() {
        lifecycleScope.launch {
            categoryViewModel.data.observe(viewLifecycleOwner, Observer { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { categoryAdapter.updateCategories(it) }
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            })
        }
    }

    private fun onCategoryClick() {
        categoryAdapter.onCategoryClick = object : CategoryAdapter.ClickListener {
            override fun onCategoryClick(category: Category.CategoryItem) {
                productsViewModel.fetchProduct(categoryId = category.categoryId.toString())
                Log.d("FeedFragment", "Category clicked: ${category.categoryId}")
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
        val dialogFragment = CustomDialogFragment.newInstance {
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
