package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.databinding.FeaturedTagsBinding
import com.example.e_commercekotlin.presentation.model.Featured

class TagsAdapter(private val items: List<Featured>) : RecyclerView.Adapter<TagsAdapter.TagViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FeaturedTagsBinding.inflate(inflater, parent, false)
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tagIcon.setImageResource(item.imageResId)
        holder.binding.tagTitle.text = item.title
    }


    override fun getItemCount() = items.size

    class TagViewHolder(val binding: FeaturedTagsBinding) : RecyclerView.ViewHolder(binding.root)
}

