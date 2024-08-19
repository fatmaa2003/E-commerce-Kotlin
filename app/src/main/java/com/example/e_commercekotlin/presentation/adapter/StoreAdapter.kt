package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.databinding.FeaturedStoreBinding
import com.example.e_commercekotlin.presentation.model.Featured

class StoreAdapter(private val items: List<Featured>) : RecyclerView.Adapter<StoreAdapter.FirstViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstViewHolder {
        val binding = FeaturedStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FirstViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FirstViewHolder, position: Int) {
        val item = items[position]
        holder.binding.imageView.setImageResource(item.imageResId)
        holder.binding.firstText.text = item.title
        holder.binding.secondText.text = item.description
    }



    override fun getItemCount() = items.size

    class FirstViewHolder(val binding: FeaturedStoreBinding) : RecyclerView.ViewHolder(binding.root)
}
