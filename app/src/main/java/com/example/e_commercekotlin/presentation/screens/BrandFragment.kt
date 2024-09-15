package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.databinding.FragmentBrandBinding
import com.example.e_commercekotlin.presentation.adapter.BrandAdapter
import com.example.e_commercekotlin.presentation.listener.StoreClickListener
import com.example.e_commercekotlin.presentation.viewmodels.StoresViewModel

class BrandFragment : Fragment(), StoreClickListener {
    private lateinit var brandAdapter: BrandAdapter
    private val storeViewModel: StoresViewModel by viewModels()
    private var _binding: FragmentBrandBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBrandBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStores()

        brandAdapter = BrandAdapter()
        binding.brandRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.brandRecyclerView.adapter = brandAdapter
    }

    private fun observeStores() {
        storeViewModel.stores.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { brandAdapter.setBrandList(it) }
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStoreClicked(storeId: String) {
        val action = BrandFragmentDirections.actionStoresToStoreFragment(storeId)
        findNavController().navigate(action)
    }
}
