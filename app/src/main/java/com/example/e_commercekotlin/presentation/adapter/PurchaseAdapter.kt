package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercekotlin.databinding.FeaturedCollectionsBinding
import com.example.e_commercekotlin.data.model.ProductItem
import com.example.e_commercekotlin.databinding.CheckOutItemBinding

class PurchaseAdapter(private var items: List<ProductItem>) : RecyclerView.Adapter<PurchaseAdapter.PurchaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseViewHolder {
        val binding = CheckOutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PurchaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PurchaseViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            Glide.with(ivcollection.context).load(item.productMainImage).into(ivcollection)
            firstText.text = item.productName
            secondText.text = "$${item.itemTotalPrice}"
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateItems(newItems: List<ProductItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    class PurchaseViewHolder(val binding: CheckOutItemBinding) : RecyclerView.ViewHolder(binding.root)
}
