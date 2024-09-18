package com.example.e_commercekotlin.presentation.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.TagsItemBinding
import com.example.e_commercekotlin.presentation.model.Featured

class FeaturedTagAdapter(
private val fragment: Fragment,
private val items: List<Featured>
) : RecyclerView.Adapter<FeaturedTagAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TagsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val featuredItem = items[position]
        holder.bind(featuredItem)

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("imageResId", featuredItem.imageResId)
                putString("title", featuredItem.title)
            }
            fragment.findNavController().navigate(R.id.actionTagsFragmentToFollowFragment, bundle)
        }
    }

    override fun getItemCount() = items.size

    inner class MyViewHolder(private val binding: TagsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Featured) {
            binding.tagIcon.setImageResource(item.imageResId)
            binding.tagTitle.text = item.title
        }
    }
}