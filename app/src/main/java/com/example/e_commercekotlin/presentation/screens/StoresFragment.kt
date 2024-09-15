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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.webkit.internal.ApiFeature
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.showToast
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.databinding.FragmentStoresBinding
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.adapter.StoreImagesAdapter
import com.example.e_commercekotlin.presentation.viewmodels.CategoryDetailsViewModel
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel
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
        handleLayoutManager()
        setRecyclerViewAdapter()
        initAdapter()
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
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.storeImagesRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }


    private fun observeCategoryDetails() {
        storeViewModel.data.observe(viewLifecycleOwner) { resources->
            when (resources) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    val productList = resources.data?.first()?.products
                    binding.progressBar.visibility = View.GONE
                    itemAdapter.setProductList(productList.orEmpty())
                    val productImageList =  productList?.mapNotNull { it.imageUrl }
                    productImageList?.let { storeImagesAdapter.setStoreImagesList(it) }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onProductClick(productId: Long, productName: String, productImage: String) {
        val dialogFragment = CustomDialogFragment()
        val bundle = Bundle().apply {
            putInt("productId", productId.toInt())
            putString("product_name", productName)
            putString("product_image", productImage)
            putString("source_fragment", "StoresFragment")
        }
        dialogFragment.arguments = bundle
        dialogFragment.show(parentFragmentManager, "CustomDialogFragment")
    }

}