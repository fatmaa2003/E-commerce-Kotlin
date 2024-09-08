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
import com.example.e_commercekotlin.databinding.FragmentFeaturedBinding
import com.example.e_commercekotlin.data.model.Product
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel
import com.example.e_commercekotlin.data.Resource

class FeaturedFragment : Fragment() {

    private var _binding: FragmentFeaturedBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryId:String
    private val viewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeaturedBinding.inflate(inflater, container, false)
        val view = binding.root


        setupProductRecyclerView()


        viewModel.data.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility= View.VISIBLE
                }
                is Resource.Success -> {
                    Log.d("in observer data success", "$resource")
                    binding.progressBar.visibility = View.GONE

                }
                is Resource.Error -> {
                    Log.d("in observer data error", "$resource")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })


        viewModel.fetchProduct(categoryId)

        return view
    }

    private fun setupProductRecyclerView() {
        productAdapter = ProductAdapter()
        binding.rvproduct.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvproduct.adapter = productAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
