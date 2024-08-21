package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.FragmentFeaturedBinding
import com.example.e_commercekotlin.presentation.adapter.CollectionsAdapter
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
        setupRecyclerView(binding.rvproductsonsale, getSliderItems2(), ProductsAdapter(getSliderItems2()))
        setupRecyclerView(binding.rvcollections, getSliderItems3(), CollectionsAdapter(getSliderItems3()))
        setupRecyclerView(binding.rvtags, getSliderItems4(), TagsAdapter(getSliderItems4()))
        setupRecyclerView(binding.rvproduct, getSliderItems5(), ProductsAdapter(getSliderItems5()))

        return view
    }



    private fun <T : RecyclerView.Adapter<*>> setupRecyclerView(recyclerView: RecyclerView, items: List<Featured>, adapter: T) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

    private fun getSliderItems1(): List<Featured> {
        return listOf(
            Featured(R.drawable.levis, "Levi's", "Denim,Casual"),
            Featured(R.drawable.levis, "Levi's", "Denim,Casual"),
            Featured(R.drawable.levis, "Levi's", "Denim,Casual"),
            Featured(R.drawable.levis, "Levi's", "Denim,Casual")
        )
    }

    private fun getSliderItems2(): List<Featured> {
        return listOf(
            Featured(R.drawable.productonsale, "Title 4", "Description 4", "45$"),
            Featured(R.drawable.productonsale, "Title 5", "Description 5", "65$"),
            Featured(R.drawable.productonsale, "Title 6", "Description 6", "77$"),
            Featured(R.drawable.productonsale, "Title 2", "Description 2", "25$"),
            Featured(R.drawable.productonsale, "Title 3", "Description 3", "100$")
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

    private fun getSliderItems5(): List<Featured> {
        return listOf(
            Featured(R.drawable.img, "Title 10", "Description 10", "10$"),
            Featured(R.drawable.img, "Title 11", "Description 11", "120$"),
            Featured(R.drawable.img, "Title 12", "Description 12", "132$")
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
