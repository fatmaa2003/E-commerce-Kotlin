package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.hide
import com.example.e_commercekotlin.Util.show
import com.example.e_commercekotlin.data.model.ProductDetailsDto
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
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.toProductItem
import com.example.e_commercekotlin.presentation.screens.Product_Details.ProductDetailsViewModel
import com.example.e_commercekotlin.presentation.viewmodels.CategoryViewModel
import com.example.e_commercekotlin.presentation.viewmodels.StoresViewModel

class FeaturedFragment : Fragment() ,ProductAdapter.ClickListener{

    private var _binding: FragmentFeaturedBinding? = null
    private val binding get() = _binding!!
    private val productsViewModel: ProductViewModel by viewModels()
    private val productDetailsViewModel : ProductDetailsViewModel by viewModels()
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductRecyclerView()
        setUpCollectionRecyclerView()
        setupTagsRecyclerView()
        observeProducts()
        setupProductRecyclerView()
        productAdapter.setListener(this)
        itemAdapter.setListener(this)
        return view
        observeStores()
        observeCollection()
    }

    private fun setUpCollectionRecyclerView() {
        collectionsAdapter = CollectionsAdapter()
        binding.rvcollections.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvcollections.adapter = collectionsAdapter
    }

    private fun observeCollection() {
        collectionViewModel.freshCollections.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { collectionsAdapter.setItems(it) }
                }
                is Resource.Error -> binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun observeProducts() {
        productsViewModel.allProduct.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }
                is Resource.Success -> {
                    Log.d("in observer data success", "$resource")
                    binding.progressBar.hide()
                    resource.data?.let { itemAdapter.setProductList(it.map { it.toProductItem() }) }
                    resource.data?.let { productAdapter.setProductList(it.map { it.toProductItem() }.reversed()) }
                }
                is Resource.Error -> {
                    Log.d("in observer data error", "$resource")
                    binding.progressBar.hide()
                }
                is Resource.Error -> binding.progressBar.visibility = View.GONE
            }
        })
    }

    override fun onProductClick(productId: Long, productName: String, productImage: String) {
        // Create a Bundle with the product details
        val bundle = Bundle().apply {
            putInt("productId", productId.toInt())
            putString("product_name", productName)
            putString("product_image", productImage)
        }

        productDetailsViewModel.fetchProductDetails(productId)

        val dialogFragment = CustomDialogFragment.newInstance {
            // Action to be executed when the user clicks in the dialog
            val action = MarketFragmentDirections.actionMarketFragmentToProductDetails(productId.toInt())
            findNavController().navigate(action)
        }

        // Set the arguments (product details) for the dialog
        dialogFragment.arguments = bundle

        // Show the dialog
        dialogFragment.show(parentFragmentManager, "CustomDialogFragment")
    }

    private fun setupProductRecyclerView() {
        itemAdapter = ProductAdapter()
        binding.rvproduct.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvproduct.adapter = itemAdapter
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
