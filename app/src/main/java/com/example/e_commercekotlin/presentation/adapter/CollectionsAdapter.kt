package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.databinding.FeaturedCollectionsBinding
import com.example.e_commercekotlin.presentation.model.Featured

class CollectionsAdapter(private val items: List<Featured>) : RecyclerView.Adapter<CollectionsAdapter.ThirdViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThirdViewHolder {
        val binding = FeaturedCollectionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ThirdViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThirdViewHolder, position: Int) {
        val item = items[position]
        holder.binding.ivcollection.setImageResource(item.imageResId)
        holder.binding.firstText.text = item.title
        holder.binding.secondText.text = item.description
    }



    override fun getItemCount() = items.size

    class ThirdViewHolder(val binding: FeaturedCollectionsBinding) : RecyclerView.ViewHolder(binding.root)
}
