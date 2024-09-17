package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.hide
import com.example.e_commercekotlin.Util.show
import com.example.e_commercekotlin.Util.showToast
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.Stores
import com.example.e_commercekotlin.databinding.FragmentBrandBinding
import com.example.e_commercekotlin.presentation.adapter.BrandAdapter
import com.example.e_commercekotlin.presentation.viewmodels.StoresViewModel

class BrandFragment : Fragment() {
    private lateinit var brandAdapter : BrandAdapter
    private val storeViewModel: StoresViewModel by viewModels()
    private var _binding: FragmentBrandBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_brand,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeStores()
        brandAdapter = BrandAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.brand)
        recyclerView.layoutManager = GridLayoutManager(requireContext(),3)
        recyclerView.adapter = brandAdapter
    }

    private fun observeStores() {
        storeViewModel.data.observe(viewLifecycleOwner, Observer { resource ->
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
        })
    }
}
