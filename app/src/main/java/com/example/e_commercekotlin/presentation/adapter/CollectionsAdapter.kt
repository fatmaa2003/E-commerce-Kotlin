package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.databinding.FeaturedCollectionsBinding
import com.example.e_commercekotlin.data.model.FreshCollection

class CollectionsAdapter() : RecyclerView.Adapter<CollectionsAdapter.CollectionViewHolder>() {

    private var items: FreshCollection? = null

    fun setItems(items: FreshCollection?){
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        val binding = FeaturedCollectionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CollectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val item = items?.get(position)
//        holder.binding.ivcollection.setImageResource(item.imageUrl?.let { holder.itemView.context.resources.getIdentifier(it, "drawable", holder.itemView.context.packageName) } ?: 0)
        Glide.with(holder.binding.ivcollection.context)
            .load(item?.imageUrl)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(holder.binding.ivcollection)
        holder.binding.firstText.text = item?.productName
        holder.binding.secondText.text = item?.description
    }

    override fun getItemCount() = items?.size ?:0

    class CollectionViewHolder(val binding: FeaturedCollectionsBinding) : RecyclerView.ViewHolder(binding.root)
}
