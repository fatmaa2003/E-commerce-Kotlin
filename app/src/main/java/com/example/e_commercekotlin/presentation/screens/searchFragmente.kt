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
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.databinding.FragmentFeedBinding
import com.example.e_commercekotlin.databinding.FragmentSearchFragmenteBinding
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel

class searchFragmente : Fragment() {

    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private var _binding: FragmentSearchFragmenteBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search_fragmente, container, false)

        // Initialize RecyclerView
        searchRecyclerView = view.findViewById(R.id.search_recycler_view)
        searchRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize Adapter
        productAdapter = ProductAdapter()
        searchRecyclerView.adapter = productAdapter

        // Observe the product data
        observeProducts()

        return view
    }

    private fun observeProducts() {
        productViewModel.data.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility= View.VISIBLE
                }
                is Resource.Success -> {
                    Log.d("in observer data success", "$resource")
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { productAdapter.setProductList(it) }

                }
                is Resource.Error -> {
                    Log.d("in observer data error", "$resource")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }
    }

