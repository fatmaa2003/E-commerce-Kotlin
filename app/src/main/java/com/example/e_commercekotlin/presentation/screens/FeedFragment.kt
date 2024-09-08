package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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
import com.example.e_commercekotlin.presentation.viewmodels.CategoryViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.e_commercekotlin.Util.setBottomNavVisibility
import com.example.e_commercekotlin.presentation.viewmodels.ProductViewModel
import com.example.e_commercekotlin.data.Resource
import kotlinx.coroutines.launch

class FeedFragment : Fragment() {
    private lateinit var itemAdapter: ProductAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    //private lateinit var categoryId: String
    private val viewModel: CategoryViewModel by viewModels()

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
        observeData()
        binding.feedFragmentToolBar.handleToolBarState(
            toolBarTitle = "Feed",
            leftIconImage = R.drawable.disk
        )
        activity?.setBottomNavVisibility(visible = true)

        val itemRecyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val categoryRecyclerView: RecyclerView = view.findViewById(R.id.categories_recycler_view)

        itemRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        itemAdapter = ProductAdapter()
        categoryAdapter = CategoryAdapter()

        itemRecyclerView.adapter = itemAdapter
        categoryRecyclerView.adapter = categoryAdapter

        onCategoryClick()
    }


        lifecycleScope.launch {
            try {
                val categories = getCategoriesFromApi()
                categoryAdapter.updateCategories(categories)

        viewModel.data.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    Log.d("in observer data success", "$resource")
                    binding.progressBar.visibility = View.GONE
                    itemAdapter.setProductList(resource.data)
                }
                is Resource.Error -> {
                    Log.d("in observer data error", "$resource")
                    binding.progressBar.visibility = View.GONE

                }
            }
        })


    }

    private fun onCategoryClick(){
        categoryAdapter.onCategoryClick = object : CategoryAdapter.ClickListener {
            override fun onCategoryClick(category: Category.CategoryItem) {
                Log.d("in category click in feed fragment"," adsdasdasdas ")
            }

        }
    }


}
