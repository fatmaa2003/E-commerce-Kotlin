package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.e_commercekotlin.Util.Constants
import com.example.e_commercekotlin.Util.setBottomNavVisibility
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.toProductItem
import com.example.e_commercekotlin.databinding.FragmentFollowBinding
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel

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
        activity?.setBottomNavVisibility(visible = false)

        val imageResId = args.imageResId
        val title = args.title

        productAdapter = ProductAdapter()
        binding.followrecycle.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.followrecycle.adapter = productAdapter
        observeProducts()

        binding.followImage.setImageResource(imageResId)
        binding.followTitle.text = title


        binding.followButton.buttonTv.text = Constants.FOLLOW
        binding.followButton.buttonTv.setOnClickListener {
            toggleFollowState()
        }
//
//        productAdapter.setListener(object : ProductAdapter.ClickListener {
//            override fun onProductClick(productId: Long, productName: String, productImage: String) {
//
//            }
//        })


    }

    private fun toggleFollowState() {
        val currentText = binding.followButton.buttonTv.text.toString()
        binding.followButton.buttonTv.text = if (currentText == Constants.FOLLOW) "Following" else Constants.FOLLOW
    }

    private fun observeProducts() {
        productsViewModel.allProduct.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility= View.VISIBLE
                }
                is Resource.Success -> {
                    handleLoadingState(false)
                    resource.data?.let {
                        productAdapter.setProductList(it.map { it.toProductItem() })}
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun handleLoadingState(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
