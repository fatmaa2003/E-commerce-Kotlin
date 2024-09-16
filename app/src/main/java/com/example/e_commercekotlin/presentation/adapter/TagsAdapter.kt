package com.example.e_commercekotlin.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.FeaturedTagsBinding
import com.example.e_commercekotlin.presentation.model.Featured

class TagsAdapter(
    private val fragment: Fragment,
    private val items: List<Featured>
) : RecyclerView.Adapter<TagsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FeaturedTagsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val featuredItem = items[position]
        holder.bind(featuredItem)


        holder.itemView.findViewById<ImageView>(R.id.tagIcon).setImageResource(featuredItem.imageResId)
        holder.itemView.findViewById<TextView>(R.id.tagTitle).text = featuredItem.title


        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("imageResId", featuredItem.imageResId)
                putString("title", featuredItem.title)
            }
            fragment.findNavController().navigate(R.id.action_feature_fragment_to_follow, bundle)
        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: FeaturedTagsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Featured) {
            // Bind data to the view
            binding.tagIcon.setImageResource(item.imageResId)
            binding.tagTitle.text = item.title
        }
    }
}
