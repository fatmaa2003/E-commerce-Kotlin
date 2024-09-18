package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.e_commercekotlin.Util.hide
import com.example.e_commercekotlin.Util.show
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.toProductItem
import com.example.e_commercekotlin.databinding.FragmentSearchFragmenteBinding
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel

class searchFragment : Fragment() , ProductAdapter.ClickListener {

    private lateinit var productAdapter: ProductAdapter
    private var _binding: FragmentSearchFragmenteBinding? = null
    private val binding get() = _binding!!
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchFragmenteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.getAllProduct()
        productAdapter = ProductAdapter()
        productAdapter.setListener(this)
        binding.searchRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.searchRecyclerView.adapter = productAdapter
        observeProducts()
        onResume()

        binding.searchBar.searchEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Filter the adapter based on the new text
                productAdapter.filter(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed after text changes
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.searchBar.searchEt.text.clear() // Clear the search text
    }

    private fun observeProducts() {
        productViewModel.allProduct.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                    binding.contentLayout.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.contentLayout.visibility = View.VISIBLE
                    binding.progressBar.hide()
                    resource.data?.map { it.toProductItem() }
                        ?.let { productAdapter.setProductList(it) }
                }
                is Resource.Error -> {
                    binding.contentLayout.visibility = View.GONE
                    binding.progressBar.hide()
                }
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

        // Create an instance of CustomDialogFragment and pass the navigation action as a lambda
        val dialogFragment = CustomDialogFragment {
            // Action to be executed when the user clicks in the dialog

            val action = searchFragmentDirections.actionSearchFragmentToProductDetails(productId.toInt())
            findNavController().navigate(action)

        }

        // Set the arguments (product details) for the dialog
        dialogFragment.arguments = bundle

        // Show the dialog
        dialogFragment.show(parentFragmentManager, "CustomDialogFragment")
    }
}

