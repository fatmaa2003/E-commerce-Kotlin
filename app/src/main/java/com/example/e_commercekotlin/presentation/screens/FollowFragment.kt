package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.e_commercekotlin.Util.Constants
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.databinding.FragmentFollowBinding
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel
import com.example.e_commercekotlin.data.model.AllProdcutsDto

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private val args: FollowFragmentArgs by navArgs()
    private val productsViewModel: ProductViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageResId = args.imageResId
        val title = args.title


        binding.followImage.setImageResource(imageResId)
        binding.followTitle.text = title
        binding.followButton.buttonTv.text=Constants.FOLLOW
        binding.followButton.buttonTv.setOnClickListener {
            val currentText = binding.followButton.buttonTv.text.toString()
            if (currentText == Constants.FOLLOW) {
                binding.followButton.buttonTv.text = "Following"
            } else {
                binding.followButton.buttonTv.text = Constants.FOLLOW
            }
        }



        productAdapter = ProductAdapter()
        binding.followrecycle.adapter = productAdapter

        observeProducts()

        productsViewModel.getAllProduct()
    }


    private fun observeProducts() {
        productsViewModel.allProduct.observe(viewLifecycleOwner) { resource ->

            handleLoadingState(resource is Resource.Loading)
            when (resource) {
                is Resource.Success -> {

                    resource.data?.let { productList ->
                        productAdapter.setProductList(productList)
                    }
                }
                is Resource.Error -> {

                    Log.e("FollowFragment", "Product Error: ${resource.message}")
                }
                else -> {}
            }
        }
    }


    private fun handleLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
