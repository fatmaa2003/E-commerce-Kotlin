package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.model.Category



class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categories = Category() // List of categories
    private var selectedPosition: Int = RecyclerView.NO_POSITION // Initially no selection
    var onCategoryClick: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.category_item_layout, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryName.text = category.name

        // Set dot visibility and text color based on the selected position
        if (position == selectedPosition) {
            holder.dotCollection.visibility = View.VISIBLE
            holder.categoryName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        } else {
            holder.dotCollection.visibility = View.GONE
            holder.categoryName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.lightgray))
        }

        // Handle item click
        holder.itemView.setOnClickListener {
            // Update the selected position and notify the adapter
            val previousPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(previousPosition)
            notifyItemChanged(selectedPosition)

            // Trigger the click listener
            onCategoryClick?.onCategoryClick(category)
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun updateCategories(categories: Category) {
        this.categories = categories
        // Set the first item as selected if the list is not empty
        if (categories.isNotEmpty()) {
            selectedPosition = 0
        } else {
            selectedPosition = RecyclerView.NO_POSITION
        }
        notifyDataSetChanged()
    }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.category_name)
        val dotCollection: View = itemView.findViewById(R.id.dotCollection) // Reference to the dot view
    }

    interface ClickListener {
        fun onCategoryClick(category: Category.CategoryItem)
    }
}
