package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.databinding.FeaturedTagsBinding
import com.example.e_commercekotlin.presentation.model.Featured

class TagsAdapter(private val items: List<Featured>) : RecyclerView.Adapter<TagsAdapter.FourthViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FourthViewHolder {
        val binding = FeaturedTagsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FourthViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FourthViewHolder, position: Int) {
        val item = items[position]
        holder.binding.imageView.setImageResource(item.imageResId)
    }

    override fun getItemCount() = items.size

    class FourthViewHolder(val binding: FeaturedTagsBinding) : RecyclerView.ViewHolder(binding.root)
}
