package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.databinding.FeaturedTagsBinding
import com.example.e_commercekotlin.presentation.model.Featured

class TagsAdapter(private val items: List<Featured>) : RecyclerView.Adapter<TagsAdapter.ViewHolder>() {

    private var onItemClickListener: ((Featured) -> Unit)? = null

    fun setOnItemClickListener(listener: (Featured) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FeaturedTagsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val featuredItem = items[position]
        holder.bind(featuredItem)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(featuredItem)
        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: FeaturedTagsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Featured) {
            binding.tagIcon.setImageResource(item.imageResId)
            binding.tagTitle.text = item.title
        }
    }
}
