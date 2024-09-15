package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.FragmentTagsBinding
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

        // Setting up RecyclerView
        val adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.featured_tags, parent, false)
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val featured = items[position]
                val tagIcon = holder.itemView.findViewById<ImageView>(R.id.tagIcon)
                val tagTitle = holder.itemView.findViewById<TextView>(R.id.tagTitle)

                tagIcon.setImageResource(featured.imageResId)
                tagTitle.text = featured.title

                holder.itemView.setOnClickListener {
                    val bundle = Bundle().apply {
                        putInt("imageResId", featured.imageResId)
                        putString("title", featured.title)
                    }
                    findNavController().navigate(R.id.actionTagsFragmentToFollowFragment, bundle)
                }
            }

            override fun getItemCount(): Int = items.size
        }

        binding.tagsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.tagsRecyclerView.adapter = adapter

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
