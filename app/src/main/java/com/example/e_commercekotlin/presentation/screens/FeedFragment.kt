package com.example.e_commercekotlin.presentation.screens



import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.handleToolBarState
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.databinding.FragmentFeedBinding
import com.example.e_commercekotlin.presentation.adapter.CategoryAdapter
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.viewmodels.CategoryViewModel
import androidx.fragment.app.viewModels

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

        val itemRecyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val categoryRecyclerView: RecyclerView = view.findViewById(R.id.categories_recycler_view)

        itemRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


        itemAdapter = ProductAdapter()
        categoryAdapter = CategoryAdapter()

        itemRecyclerView.adapter = itemAdapter
        categoryRecyclerView.adapter = categoryAdapter


    }

    private fun observeData() {
        viewModel.data.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.d("FeedFragment", "Loading: $resource")
                }
                is Resource.Success -> {
                    Log.d("FeedFragment", "Success: $resource")
                    resource.data?.let { categoryAdapter.updateCategories(it) }
                }
                is Resource.Error -> {
                    Log.d("FeedFragment", "Error: ${resource.message}")
                }
            }
        })
    }

//    private fun handleToolBarStatus() {
//        binding.feedFragmentToolBar.toolbarTitle.text = getString(R.string.feed_tool_bar_title)
//        binding.feedFragmentToolBar.placeHolderIcon.setImageResource(R.drawable.search_icon)
//        binding.feedFragmentToolBar.leftIcon.setImageResource(R.drawable.disk)
//
//    }


}
