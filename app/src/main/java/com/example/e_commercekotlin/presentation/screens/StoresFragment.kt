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

    private lateinit var itemAdapter: ProductAdapter
    private lateinit var storeImagesAdapter: StoreImagesAdapter
    private val storesViewModel: StoresViewModel by viewModels()
    private var _binding: FragmentStoresBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stores, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStores()


        val itemRecyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val storeImagesRecyclerView: RecyclerView = view.findViewById(R.id.storeImagesRv)

        itemRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        storeImagesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        itemAdapter = ProductAdapter()
        storeImagesAdapter = StoreImagesAdapter()

        itemRecyclerView.adapter = itemAdapter
        storeImagesRecyclerView.adapter = storeImagesAdapter

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