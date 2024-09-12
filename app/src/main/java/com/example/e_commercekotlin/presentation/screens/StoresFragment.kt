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
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.databinding.FragmentStoresBinding
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.adapter.StoreImagesAdapter
import com.example.e_commercekotlin.presentation.viewmodels.StoresViewModel

class StoresFragment : Fragment() {

    private var _binding: FragmentStoresBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemAdapter: ProductAdapter
    private lateinit var storeImagesAdapter: StoreImagesAdapter
    private val storesViewModel: StoresViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoresBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStores()

        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.storeImagesRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        itemAdapter = ProductAdapter()
        storeImagesAdapter = StoreImagesAdapter()

        binding.recyclerView.adapter = itemAdapter
        binding.storeImagesRv.adapter = storeImagesAdapter

    }

    private fun observeStores() {
        storesViewModel.data.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    Log.d("in observer data success", "$resource")
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { storesList ->
                        Log.e("hanan&fatma", "observeStores: " + storesList )
                        storeImagesAdapter.setStoreImagesList(storesList)
                    }
                }
                is Resource.Error -> {
                    Log.d("in observer data error", "$resource")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }
}