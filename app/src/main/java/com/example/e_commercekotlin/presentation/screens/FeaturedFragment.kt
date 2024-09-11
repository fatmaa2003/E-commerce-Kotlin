package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.model.ProductDetailsDto
import com.example.e_commercekotlin.databinding.FragmentFeaturedBinding
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.presentation.viewmodels.CategoryViewModel

class FeaturedFragment : Fragment() {

    private var _binding: FragmentFeaturedBinding? = null
    private val binding get() = _binding!!
    private val productsViewModel: ProductViewModel by viewModels()
    private lateinit var itemAdapter: ProductAdapter
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeaturedBinding.inflate(inflater, container, false)
        val view = binding.root
        productsViewModel.getAllProduct()
        observeProducts()
        setupProductRecyclerView()

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

    private fun setupProductRecyclerView() {
        itemAdapter = ProductAdapter()
        binding.rvproduct.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvproduct.adapter = itemAdapter
        productAdapter = ProductAdapter()
        binding.rvproductsonsale.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvproductsonsale.adapter = productAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
