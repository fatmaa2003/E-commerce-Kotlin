package com.example.e_commercekotlin.presentation.screens



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.ApiService
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.data.model.Product
import com.example.e_commercekotlin.databinding.FragmentFeedBinding
import com.example.e_commercekotlin.presentation.adapter.CategoryAdapter
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FeedFragment : Fragment() {

    private lateinit var itemAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private var _binding: FragmentFeedBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleToolBarStatus()

        val itemRecyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val categoryRecyclerView: RecyclerView = view.findViewById(R.id.categories_recycler_view)

        itemRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        itemAdapter = ProductAdapter()
        categoryAdapter = CategoryAdapter(ArrayList()) { category ->

        }

        itemRecyclerView.adapter = itemAdapter
        categoryRecyclerView.adapter = categoryAdapter


        lifecycleScope.launch {
            try {
                val categories = getCategoriesFromApi()
                categoryAdapter.updateCategories(categories)

                val items = getItemsFromApi()
                itemAdapter.setProductList(items)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireActivity(), "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleToolBarStatus() {
        binding.feedFragmentToolBar.toolbarTitle.text = getString(R.string.feed_tool_bar_title)
//        binding.feedFragmentToolBar.placeHolderIcon.setImageResource()
    }

    private suspend fun getCategoriesFromApi(): List<Category> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(ApiService::class.java)
        return api.getCategories()
    }


    private suspend fun getItemsFromApi(): List<Product> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(ApiService::class.java)
        return api.getItems()
    }
}
