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
import com.example.e_commercekotlin.data.model.Stores
import com.example.e_commercekotlin.databinding.FragmentFeaturedBinding
import com.example.e_commercekotlin.presentation.adapter.CollectionsAdapter
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.adapter.StoreAdapter
import com.example.e_commercekotlin.presentation.adapter.TagsAdapter
import com.example.e_commercekotlin.presentation.model.Featured
import com.example.e_commercekotlin.presentation.viewmodels.CollectionViewModel
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel
import com.example.e_commercekotlin.presentation.viewmodels.StoresViewModel

class FeaturedFragment : Fragment() {

    private var _binding: FragmentFeaturedBinding? = null
    private val binding get() = _binding!!
    private val productsViewModel: ProductViewModel by viewModels()
    private val storesViewModel: StoresViewModel by viewModels()
    private val collectionViewModel: CollectionViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var tagsAdapter: TagsAdapter
    private lateinit var collectionsAdapter: CollectionsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeaturedBinding.inflate(inflater, container, false)
        productsViewModel.getAllProduct()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        observeProducts()
        observeStores()
        observeFreshCollections()
    }

    private fun observeProducts() {
        productsViewModel.allProduct.observe(viewLifecycleOwner, Observer { resource ->
            handleLoadingState(resource is Resource.Loading)
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { productList ->
                        productAdapter.setProductList(productList)
                    }
                }
                is Resource.Error -> {
                    Log.e("FeaturedFragment", "Product Error: ${resource.message}")
                }
                else -> {}
            }
        })
    }

    private fun observeFreshCollections() {
        collectionViewModel.freshCollections.observe(viewLifecycleOwner, Observer { resource ->
            handleLoadingState(resource is Resource.Loading)
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { freshCollectionList ->
                        Log.e("FeaturedFragmentt", "Fresh Collection : ${resource.message}")
                        collectionsAdapter.setItems(freshCollectionList)
                    }
                }
                is Resource.Error -> {
                    Log.e("FeaturedFragment", "Fresh Collection Error: ${resource.message}")
                }
                else -> {}
            }
        })
    }

    private fun observeStores() {
        storesViewModel.stores.observe(viewLifecycleOwner, Observer { resource ->
            handleLoadingState(resource is Resource.Loading)
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { storesList ->
                        storeAdapter = StoreAdapter(storesList)
                        binding.rvstores.adapter = storeAdapter
                    }
                }
                is Resource.Error -> {
                    Log.e("FeaturedFragment", "Store Error: ${resource.message}")
                }
                else -> {}
            }
        })
    }

    private fun setupRecyclerViews() {

        productAdapter = ProductAdapter()
        setupRecyclerView(binding.rvproduct, productAdapter)


        setupRecyclerView(binding.rvproductsonsale, productAdapter)


        val tagsList = getDummyTags()
        tagsAdapter = TagsAdapter(this, tagsList)
        setupRecyclerView(binding.rvtags, tagsAdapter)


        storeAdapter = StoreAdapter(Stores())
        setupRecyclerView(binding.rvstores, storeAdapter)

        collectionsAdapter = CollectionsAdapter()
        binding.rvcollections.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvcollections.adapter = collectionsAdapter



    }

    private fun setupRecyclerView(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

    private fun getDummyTags(): List<Featured> {
        return listOf(
            Featured(R.drawable.tag3, "Sustainable"),
            Featured(R.drawable.tag2, "Luxury"),
            Featured(R.drawable.tag1, "Glam")
        )
    }

    private fun handleLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}