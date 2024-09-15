package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.databinding.FragmentFeaturedBinding
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.adapter.StoreAdapter
import com.example.e_commercekotlin.presentation.adapter.TagsAdapter
import com.example.e_commercekotlin.presentation.model.Featured
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel
import com.example.e_commercekotlin.presentation.viewmodels.StoresViewModel

class FeaturedFragment : Fragment() {

    private var _binding: FragmentFeaturedBinding? = null
    private val binding get() = _binding!!
    private val productsViewModel: ProductViewModel by viewModels()
    private val storesViewModel: StoresViewModel by viewModels()
    private lateinit var itemAdapter: ProductAdapter
    private lateinit var productAdapter: ProductAdapter
    private lateinit var tagsAdapter: TagsAdapter
    private lateinit var storeAdapter: StoreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeaturedBinding.inflate(inflater, container, false)
        val view = binding.root
        productsViewModel.getAllProduct()
        storesViewModel.fetchStores()
        observeProducts()
        observeStores()
        setupProductRecyclerView()
        setupTagsRecyclerView()

        return view
    }

    private fun observeProducts() {
        productsViewModel.allProduct.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility= View.VISIBLE
                }
                is Resource.Success -> {
                    Log.d("in observer data success", "$resource")
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { itemAdapter.setProductList(it.products.orEmpty()) }
                    resource.data?.let { productAdapter.setProductList(it.products.orEmpty()) }
                }
                is Resource.Error -> {
                    Log.d("in observer data error", "$resource")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun observeStores() {
        storesViewModel.stores.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    Log.d("FeaturedFragment", "Stores Data: ${resource.data}")
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let {
                        storeAdapter = StoreAdapter(it)
                        binding.rvstores.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        binding.rvstores.adapter = storeAdapter
                    }
                }
                is Resource.Error -> {
                    Log.d("FeaturedFragment", "Error: ${resource.message}")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun setupProductRecyclerView() {
        itemAdapter = ProductAdapter()
        binding.rvproduct.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvproduct.adapter = itemAdapter
        productAdapter = ProductAdapter()
        binding.rvproductsonsale.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvproductsonsale.adapter = productAdapter
    }

    private fun setupTagsRecyclerView() {
        val tagsList = getDummyTags()
        tagsAdapter = TagsAdapter(tagsList)
        binding.rvtags.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvtags.adapter = tagsAdapter
    }

    private fun getDummyTags(): List<Featured> {
        return listOf(
            Featured(R.drawable.tag3, "Sustainable"),
            Featured(R.drawable.tag2, "Luxury"),
            Featured(R.drawable.tag1, "Glam")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
