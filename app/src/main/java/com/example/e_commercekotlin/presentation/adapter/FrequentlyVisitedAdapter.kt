package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.model.FrequentlyVisitedItems
import com.example.e_commercekotlin.data.model.ProductResponse


class FrequentlyVisitedAdapter() : RecyclerView.Adapter<FrequentlyVisitedAdapter.MyViewHolder>() {

    private var freqVisitedList : ProductResponse = ProductResponse()

    fun setFrequentlyVisitedItemsList(freqVisitedList: ProductResponse){
        this.freqVisitedList = freqVisitedList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.frequently_visited_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = freqVisitedList[position]

        holder.freqVisitedItemType.text = currentItem.productName
        holder.freqVisitedItemCategory.text = currentItem.price.toString()
    }

    override fun getItemCount(): Int {
        return freqVisitedList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val freqVisitedItemImage: ImageView = itemView.findViewById(R.id.freq_visited_item_image)
        val freqVisitedItemType: TextView = itemView.findViewById(R.id.freq_visited_type_text)
        val freqVisitedItemCategory: TextView = itemView.findViewById(R.id.freq_visited_category_text)
    }
}
