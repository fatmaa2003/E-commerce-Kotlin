package com.example.e_commercekotlin.presentation.screens

import android.app.Notification.Action
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.handleToolBarState
import com.example.e_commercekotlin.Util.setBottomNavVisibility
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.databinding.FragmentFeedBinding
import com.example.e_commercekotlin.presentation.adapter.CategoryAdapter
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.screens.Product_Details.ProductDetailsFragment
import com.example.e_commercekotlin.presentation.screens.Product_Details.ProductDetailsViewModel
import com.example.e_commercekotlin.presentation.viewmodels.CategoryViewModel
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel
import kotlinx.coroutines.launch

class FeedFragment : Fragment(), ProductAdapter.ClickListener{
    private lateinit var itemAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    //private lateinit var categoryId: String
    private val categoryViewModel: CategoryViewModel by viewModels()
    private val productsViewModel: ProductViewModel by viewModels()
    private val productDetailsViewModel : ProductDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        observeProducts()
        binding.feedFragmentToolBar.handleToolBarState(
            toolBarTitle = "Feed", leftIconImage = R.drawable.disk
        )
        activity?.setBottomNavVisibility(visible = true)

        val itemRecyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val categoryRecyclerView: RecyclerView = view.findViewById(R.id.categories_recycler_view)

        itemRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        itemAdapter = ProductAdapter()
        categoryAdapter = CategoryAdapter()
        itemAdapter.setListener(this)

        itemRecyclerView.adapter = itemAdapter
        categoryRecyclerView.adapter = categoryAdapter

        onCategoryClick()
    }
    private fun observeProducts() {
        productsViewModel.data.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility= View.VISIBLE
                }
                is Resource.Success -> {
                    //caching in room
                    resource.data?.let { productsViewModel.cacheProducts(it) }

                    Log.d("in observer data success", "$resource")
                    binding.progressBar.visibility = View.GONE

                    //list from api
                    resource.data?.let { itemAdapter.setProductList(it) }

                    //get list from room
                    productsViewModel.getProducts()
                    productsViewModel.productData.observe(viewLifecycleOwner,{

                    })
                }
                is Resource.Error -> {
                    Log.d("in observer data error", "$resource")
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    private fun observeData(){
        lifecycleScope.launch {
            categoryViewModel.data.observe(viewLifecycleOwner, Observer { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        resource.data?.let { categoryViewModel.cacheCategory(it) }
                        Log.d("in observer data success", "$resource")
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { categoryAdapter.updateCategories(it) }
                    }

                    is Resource.Error -> {
                        Log.d("in observer data error", "$resource")
                        binding.progressBar.visibility = View.GONE
                    }
                }
            })
        }
    }
    private fun onCategoryClick() {
        categoryAdapter.onCategoryClick = object : CategoryAdapter.ClickListener {
            override fun onCategoryClick(category: Category.CategoryItem) {
                productsViewModel.fetchProduct(categoryId = category.categoryId.toString())
                Log.d("in category click in feed fragment", " ${category.categoryId}")
            }
        }
    }


    override fun onProductClick(productId: Long) {
        val bundle = Bundle().apply {
            putInt("productId", productId.toInt())
        }
        findNavController().navigate(R.id.action_Feed_fragment_to_product_details, bundle)
    }


}
