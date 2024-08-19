package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.databinding.FeaturedProductBinding
import com.example.e_commercekotlin.presentation.model.Featured

class ProductsAdapter(private val items: List<Featured>) : RecyclerView.Adapter<ProductsAdapter.SecondViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondViewHolder {
        val binding = FeaturedProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SecondViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SecondViewHolder, position: Int) {
        val item = items[position]
        holder.binding.imageView.setImageResource(item.imageResId)
        holder.binding.firstText.text = item.title
        holder.binding.secondText.text = item.description
        holder.binding.priceText.text = item.price
    }

    override fun getItemCount() = items.size

    class SecondViewHolder(val binding: FeaturedProductBinding) : RecyclerView.ViewHolder(binding.root)
}
