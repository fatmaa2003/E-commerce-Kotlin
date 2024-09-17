package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.RetrofitInstance
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.presentation.adapter.CategoryAdapter
import com.example.e_commercekotlin.presentation.adapter.CollectionPageAdapter
import com.example.e_commercekotlin.presentation.adapter.FrequentlyVisitedAdapter
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel
import kotlinx.coroutines.launch

class CollectionFragment : Fragment() {

    private lateinit var collectionPageAdapter: CollectionPageAdapter
    private lateinit var frequentlyVisitedAdapter: FrequentlyVisitedAdapter
    private lateinit var toggleTextView: TextView
    private lateinit var categoryAdapter: CategoryAdapter
    private val productsViewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val collectionRecyclerView: RecyclerView = view.findViewById(R.id.collection_recycler_view)
        val freqVisitedRecyclerView: RecyclerView = view.findViewById(R.id.frequently_visited_recycler_view)
        toggleTextView = view.findViewById(R.id.toggle_text_view)

        collectionRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        freqVisitedRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        collectionPageAdapter = CollectionPageAdapter()
        frequentlyVisitedAdapter = FrequentlyVisitedAdapter()

        collectionRecyclerView.adapter = collectionPageAdapter
        freqVisitedRecyclerView.adapter = frequentlyVisitedAdapter
        fetchCategories()
        fetchProducts("1")

        toggleTextView.setOnClickListener {
            collectionPageAdapter.toggleShowItems()
            updateToggleTextView()
        }

        collectionPageAdapter.onCollectionClickListener = { categoryItem ->
            // Handle the category click (e.g., fetch products or navigate to another fragment)
            Log.d("Category Clicked", "Category ID: ${categoryItem.categoryId}, Name: ${categoryItem.name}")

            val bundle = Bundle().apply {
                putString("categoryId", categoryItem.categoryId.toString())
                putString("categoryName", categoryItem.name.toString())
            }
            findNavController().navigate(R.id.action_market_fragment_to_collection_details, bundle)
        }

    }

    private fun fetchCategories() {
        lifecycleScope.launch {
            try {
                val categoriesResponse = RetrofitInstance.api.getCategories()
                if (categoriesResponse.isSuccessful) {
                    val categories = categoriesResponse.body()
                    collectionPageAdapter.setCollectionList(categories!!)
                } else {
                    Toast.makeText(requireContext(), "Failed to load categories", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchProducts(categoryId: String) {
        lifecycleScope.launch {
            try {
                // Assuming getProductsByCategoryId is a suspend function
                val productsResponse = RetrofitInstance.api.getProductsByCategoryId(categoryId)
                if (productsResponse.isSuccessful) {
                    val products = productsResponse.body()
                    if (products != null) {
                        frequentlyVisitedAdapter.setFrequentlyVisitedItemsList(products)
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load products", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateToggleTextView() {
        if (collectionPageAdapter.isShowingAllItems()) {
            toggleTextView.text = "Show Less"
            toggleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.showless, 0, 0, 0)
        } else {
            toggleTextView.text = "Show More"
            toggleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.showmore, 0, 0, 0)
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
}