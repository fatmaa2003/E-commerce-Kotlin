package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.webkit.internal.ApiFeature
import com.bumptech.glide.Glide
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.hide
import com.example.e_commercekotlin.Util.show
import com.example.e_commercekotlin.Util.showToast
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.databinding.FragmentStoresBinding
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.adapter.StoreImagesAdapter
import com.example.e_commercekotlin.presentation.viewmodels.StoresViewModel

class StoresFragment : Fragment(), ProductAdapter.ClickListener {

    private var _binding: FragmentStoresBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemAdapter: ProductAdapter
    private lateinit var storeImagesAdapter: StoreImagesAdapter
    private val storeViewModel: StoresViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val storeId = arguments?.getString("storeId")
        storeViewModel.fetchCategoryDetails(storeId.orEmpty())
        observeCategoryDetails()
        initAdapter()
        handleLayoutManager()
        setRecyclerViewAdapter()
        setListener()
    }

    private fun setListener() {
        itemAdapter.setListener(this)
    }

    private fun initAdapter() {
        itemAdapter = ProductAdapter()
        storeImagesAdapter = StoreImagesAdapter()
    }

    private fun setRecyclerViewAdapter() {
        binding.recyclerView.adapter = itemAdapter
        binding.storeImagesRv.adapter = storeImagesAdapter
    }

    private fun handleLayoutManager() {
        binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.storeImagesRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun observeCategoryDetails() {
        storeViewModel.data.observe(viewLifecycleOwner) { resources ->
            when (resources) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }

                is Resource.Success -> {
                    val productList = resources.data?.category?.products
                    binding.progressBar.hide()
                    itemAdapter.setProductList(productList.orEmpty())
                    val productImageList = productList?.mapNotNull { it.imageUrl }
                    productImageList?.let { storeImagesAdapter.setStoreImagesList(it) }
                    binding.storeName.text = resources.data?.category?.name
                    binding.storeDescription.text = resources.data?.category?.description
                    Glide.with(binding.storeImage.image.context)
                        .load(resources.data?.category?.image_url).into(binding.storeImage.image)
                }

                is Resource.Error -> {
                    binding.progressBar.hide()
                }
            }
        }
    }

    override fun onProductClick(productId: Long, productName: String, productImage: String) {
//         Create a Bundle with the product details
        val bundle = Bundle().apply {
            putInt("productId", productId.toInt())
            putString("product_name", productName)
            putString("product_image", productImage)
            putString("source_fragment", "FeedFragment")

        }

        // Create an instance of CustomDialogFragment and pass the navigation action as a lambda
        val dialogFragment = CustomDialogFragment.newInstance {
            // Action to be executed when the user clicks in the dialog
            val action = StoresFragmentDirections.actionStoreToProductDetails(productId.toInt())
            findNavController().navigate(action)
        }

        // Set the arguments (product details) for the dialog
        dialogFragment.arguments = bundle

        // Show the dialog
        dialogFragment.show(parentFragmentManager, "CustomDialogFragment")

    }

}