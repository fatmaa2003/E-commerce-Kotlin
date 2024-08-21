package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.model.Product
import com.example.e_commercekotlin.databinding.FragmentFeaturedBinding
import com.example.e_commercekotlin.presentation.adapter.CollectionsAdapter
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter
import com.example.e_commercekotlin.presentation.adapter.ProductsAdapter
import com.example.e_commercekotlin.presentation.adapter.StoreAdapter
import com.example.e_commercekotlin.presentation.adapter.TagsAdapter
import com.example.e_commercekotlin.presentation.model.Featured

class FeaturedFragment : Fragment() {

    private var _binding: FragmentFeaturedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeaturedBinding.inflate(inflater, container, false)
        val view = binding.root


        setupRecyclerView(binding.rvstores, getSliderItems1(), StoreAdapter(getSliderItems1()))
        setupRecyclerView(binding.rvproductsonsale, getSliderItems2(), ProductAdapter())
        setupRecyclerView(binding.rvcollections, getSliderItems3(), CollectionsAdapter(getSliderItems3()))
        setupRecyclerView(binding.rvtags, getSliderItems4(), TagsAdapter(getSliderItems4()))
        setupRecyclerView(binding.rvproduct, getSliderItems5(), ProductAdapter())

        return view
    }

    private fun <T : RecyclerView.Adapter<*>> setupRecyclerView(recyclerView: RecyclerView, items: List<*>, adapter: T) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
        if (adapter is ProductAdapter && items is List<*>) {
            @Suppress("UNCHECKED_CAST")
            adapter.setProductList(items as List<Product>)
        }
    }

    private fun getSliderItems1(): List<Featured> {
        return listOf(
            Featured(R.drawable.levis, "Levi's", "Denim,Casual"),
            Featured(R.drawable.levis, "Levi's", "Denim,Casual"),
            Featured(R.drawable.levis, "Levi's", "Denim,Casual"),
            Featured(R.drawable.levis, "Levi's", "Denim,Casual")
        )
    }

    private fun getSliderItems2(): List<Product> {
        return listOf(
            Product( title = "Title 4", price = 100, images = listOf("https://i.imgur.com/MxJyADq.jpeg")),
            Product( title = "Title 5", price = 200, images = listOf("https://i.imgur.com/MxJyADq.jpeg")),
            Product( title = "Title 6", price = 300, images = listOf("https://i.imgur.com/MxJyADq.jpeg")),
            Product( title = "Title 7", price = 100, images = listOf("https://i.imgur.com/MxJyADq.jpeg")),
            Product( title = "Title 8", price = 100, images = listOf("https://i.imgur.com/MxJyADq.jpeg"))
        )
    }

    private fun getSliderItems3(): List<Featured> {
        return listOf(
            Featured(R.drawable.collectionimage, "Title 7", "Description 7"),
            Featured(R.drawable.collectionimage, "Title 8", "Description 8"),
            Featured(R.drawable.collectionimage, "Title 9", "Description 9")
        )
    }

    private fun getSliderItems4(): List<Featured> {
        return listOf(
            Featured(R.drawable.tagimage, "Sustainable", ""),
            Featured(R.drawable.tagimage, "Sustainable", ""),
            Featured(R.drawable.tagimage, "Sustainable", ""),
            Featured(R.drawable.tagimage, "Sustainable", ""),
            Featured(R.drawable.tagimage, "Sustainable", ""),
            Featured(R.drawable.tagimage, "Sustainable", "")
        )
    }

    private fun getSliderItems5(): List<Product> {
        return listOf(
            Product( title = "Title 4", price = 100, images = listOf("https://i.imgur.com/MxJyADq.jpeg")),
            Product( title = "Title 5", price = 200, images = listOf("https://i.imgur.com/MxJyADq.jpeg")),
            Product( title = "Title 6", price = 300, images = listOf("https://i.imgur.com/MxJyADq.jpeg")),
            Product( title = "Title 7", price = 100, images = listOf("https://i.imgur.com/MxJyADq.jpeg")),
            Product( title = "Title 8", price = 100, images = listOf("https://i.imgur.com/MxJyADq.jpeg"))
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
