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
import com.example.e_commercekotlin.data.model.toProductItem
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
    private lateinit var itemAdapter: ProductAdapter
    private lateinit var productAdapter: ProductAdapter
    private lateinit var tagsAdapter: TagsAdapter
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var collectionsAdapter: CollectionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeaturedBinding.inflate(inflater, container, false)
        val view = binding.root
        productsViewModel.getAllProduct()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductRecyclerView()
        setUpCollectionRecyclerView()
        setupTagsRecyclerView()
        observeProducts()
        observeStores()
        observeCollection()
    }

    private fun setUpCollectionRecyclerView() {
        collectionsAdapter = CollectionsAdapter()
        binding.rvcollections.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvcollections.adapter = collectionsAdapter
    }

    private fun observeCollection() {
        collectionViewModel.freshCollections.observe(viewLifecycleOwner) { resources->
            when(resources){
                is Resource.Loading -> {
                    binding.progressBar.visibility= View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resources.data.let { collectionsAdapter.setItems(it) }
                }
                is Resource.Error -> {}
            }
        }
    }

    private fun observeProducts() {
        productsViewModel.allProduct.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility= View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { itemAdapter.setProductList(it.map { it.toProductItem() }) }
                    resource.data?.let { productAdapter.setProductList(it.map { it.toProductItem() }.reversed()) }
                }
                is Resource.Error -> {
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
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let {
                        storeAdapter = StoreAdapter(it)
                        binding.rvstores.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                        binding.rvstores.adapter = storeAdapter
                    }
                }
                is Resource.Error -> {
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
        productAdapter.setFullWidth(false)
    }

    private fun setupTagsRecyclerView() {
        val tagsList = getDummyTags()
        tagsAdapter = TagsAdapter(this, tagsList)
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
