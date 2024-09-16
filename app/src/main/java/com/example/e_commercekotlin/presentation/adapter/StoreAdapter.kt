package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.FeaturedStoreBinding
import com.example.e_commercekotlin.data.model.Stores
import com.google.android.material.imageview.ShapeableImageView

class StoreAdapter(private val stores: Stores) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val binding = FeaturedStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = stores[position]
        val storeImage: ShapeableImageView = holder.itemView.findViewById(R.id.imageView)

        Glide.with(holder.binding.imageView.context)
            .load(store.marketImage)
            .into(storeImage)
        holder.binding.firstText.text = store.name ?: "Unknown"
       // holder.binding.secondText.text = store.description ?: "No description"
    }

    override fun getItemCount(): Int = stores.size

    class StoreViewHolder(val binding: FeaturedStoreBinding) : RecyclerView.ViewHolder(binding.root)
}
