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
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.hide
import com.example.e_commercekotlin.Util.show
import com.example.e_commercekotlin.Util.showToast
import com.example.e_commercekotlin.Util.setBottomNavVisibility
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
        activity?.setBottomNavVisibility(visible = false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        brandAdapter = BrandAdapter()
        setListener()
        binding.brandRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.brandRecyclerView.adapter = brandAdapter
        observeStores()
    }

    private fun setListener() {
        brandAdapter.setStoreClickListener(this)
    }

    private fun observeStores() {
        storeViewModel.stores.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }

                is Resource.Success -> {
                    binding.progressBar.hide()
                    resource.data?.let { brandAdapter.setBrandList(it) }
                }

                is Resource.Error -> {
                    binding.progressBar.hide()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStoreClicked(storeId: String) {
        val action = MarketFragmentDirections.actionMarketFragmentToStore(storeId)
        findNavController().navigate(action)
    }
}