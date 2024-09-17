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
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.handleToolBarState
import com.example.e_commercekotlin.Util.hide
import com.example.e_commercekotlin.Util.show
import com.example.e_commercekotlin.Util.setBottomNavVisibility
import com.example.e_commercekotlin.Util.showToast
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.databinding.FragmentDressesDetailsBinding
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.screens.CustomDialogFragment
import com.example.e_commercekotlin.presentation.screens.StoresFragmentDirections
import com.example.e_commercekotlin.presentation.viewmodels.CategoryDetailsViewModel

class DressesDetails : Fragment(), ProductAdapter.ClickListener {

    private var _binding: FragmentDressesDetailsBinding? = null
    private val binding get() = _binding!!
    private val categoryDetailsViewModel: CategoryDetailsViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryName : String
    private lateinit var categoryId : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         categoryId = arguments?.getString("categoryId").orEmpty()
         categoryName = arguments?.getString("categoryName").orEmpty()
        categoryDetailsViewModel.fetchCategoryDetails(categoryId)
        _binding = FragmentDressesDetailsBinding.inflate(inflater, container, false)
        activity?.setBottomNavVisibility(visible = false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productAdapter = ProductAdapter()
        setupUI(categoryName)
        productAdapter.setListener(this)

        observeCategoryDetails()
    }

    private fun setupUI(categoryName: String?) {
        binding.fragmentDressDetailsToolbar.handleToolBarState(leftIconImage = R.drawable.back)
        binding.titleCategoryDetails.text = categoryName
        binding.verticalRecyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.verticalRecyclerView.adapter = productAdapter

    }

    private fun observeCategoryDetails() {
        categoryDetailsViewModel.data.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let {
                        val productList = it.category?.products
                        productAdapter.setProductList(productList.orEmpty())
                        binding.productCountText.text = "${it.category?.products?.size} " + "products"
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    showToast(message = resource.message.toString())
                    Log.e("Tag123", "observeCategoryDetails: " + resource.message.toString() )
                }
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onProductClick(productId: Long, productName: String, productImage: String) {
        // Create a Bundle with the product details
        val bundle = Bundle().apply {
            putInt("productId", productId.toInt())
            putString("product_name", productName)
            putString("product_image", productImage)
            putString("source_fragment", "DressesDetails")

        }

        // Create an instance of CustomDialogFragment and pass the navigation action as a lambda
        val dialogFragment = CustomDialogFragment {
            // Action to be executed when the user clicks in the dialog
            val action = DressesDetailsDirections.actionCollectionDetailsToProductDetails2(productId.toInt())
            findNavController().navigate(action)
        }

        // Set the arguments (product details) for the dialog
        dialogFragment.arguments = bundle

        // Show the dialog
        dialogFragment.show(parentFragmentManager, "CustomDialogFragment")

    }
}