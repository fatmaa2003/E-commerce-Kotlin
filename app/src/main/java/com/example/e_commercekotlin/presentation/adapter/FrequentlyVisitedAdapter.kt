package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.model.ProductResponse
import com.example.e_commercekotlin.presentation.adapter.ProductAdapter.ClickListener


class FrequentlyVisitedAdapter() : RecyclerView.Adapter<FrequentlyVisitedAdapter.MyViewHolder>() {

    private var freqVisitedList : ProductResponse = ProductResponse()
    lateinit var onProductClick : OnFrequentlyProductClick

    fun setListener(onProductClick: OnFrequentlyProductClick) {
        this.onProductClick = onProductClick
    }

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
        Glide.with(holder.freqVisitedItemImage.context).load(currentItem.imageUrl).into(holder.freqVisitedItemImage)

        holder.item.setOnClickListener {
            currentItem.let {
                it.productId?.toLong()?.let { it1 ->
                    onProductClick.onProductClick(
                        productId = it1,
                        productImage = currentItem.imageUrl.orEmpty() ,
                        productName = currentItem.productName.orEmpty()
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return freqVisitedList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val freqVisitedItemImage: ImageView = itemView.findViewById(R.id.freq_visited_item_image)
        val freqVisitedItemType: TextView = itemView.findViewById(R.id.freq_visited_type_text)
        val freqVisitedItemCategory: TextView = itemView.findViewById(R.id.freq_visited_category_text)
        val item : RelativeLayout = itemView.findViewById(R.id.layout)
    }

    interface OnFrequentlyProductClick{
        fun onProductClick(productId: Long, productName: String, productImage: String)
    }
}
