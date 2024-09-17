package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.setBottomNavVisibility
import com.example.e_commercekotlin.databinding.FragmentTagsBinding
import com.example.e_commercekotlin.presentation.adapter.TagsAdapter
import com.example.e_commercekotlin.presentation.model.Featured

class TagsFragment : Fragment() {

    private var _binding: FragmentTagsBinding? = null
    private val binding get() = _binding!!

    private val items = listOf(
        Featured(R.drawable.tag1, "Sustainable"),
        Featured(R.drawable.tag2, "Luxury"),
        Featured(R.drawable.tag3, "Glam")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTagsBinding.inflate(inflater, container, false)
        val view = binding.root

        activity?.setBottomNavVisibility(visible = false)


        val adapter = TagsAdapter(this, items)
        binding.tagsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.tagsRecyclerView.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
