package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.data.model.Collection
import com.example.e_commercekotlin.presentation.listener.StoreClickListener


class CollectionPageAdapter : RecyclerView.Adapter<CollectionPageAdapter.MyViewHolder>() {

    private var collectionList: Category = Category()
    private var showAllItems: Boolean = false

    // Click listener interface
    var onCollectionClickListener: ((Category.CategoryItem) -> Unit)? = null

    fun setCollectionList(collectionList: Category) {
        this.collectionList = collectionList
        notifyDataSetChanged()
    }

    fun toggleShowItems() {
        showAllItems = !showAllItems
        notifyDataSetChanged()
    }

    fun isShowingAllItems(): Boolean {
        return showAllItems
    }

    override fun getItemCount(): Int {
        return if (showAllItems) {
            collectionList.size
        } else {
            minOf(collectionList.size, 4)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.collection_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = collectionList[position]
        holder.collectionName.text = currentItem.name

        // Customizing item based on position
        when (position) {
            0 -> {
                holder.collectionName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.salered))
                holder.itemView.setBackgroundResource(R.drawable.red_bckground)
            }
            1 -> {
                holder.collectionName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.purple))
                holder.itemView.setBackgroundResource(R.drawable.purple_background)
            }
            else -> {
                holder.collectionName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.dark_surface))
                holder.itemView.setBackgroundResource(R.drawable.neutral_grey_rounded_background)
            }
        }

        // Set click listener for each item
        holder.itemView.setOnClickListener {
            onCollectionClickListener?.invoke(currentItem) // Trigger the click event
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val collectionName: TextView = itemView.findViewById(R.id.collection_name)
    }
}
