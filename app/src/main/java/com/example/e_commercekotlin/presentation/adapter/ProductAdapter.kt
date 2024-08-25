package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.model.Product
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter() : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    private var productList : List<Product> = listOf()

    fun setProductList(productList : List<Product>){
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productList[position]

        Picasso.get()
            .load(currentItem.images.firstOrNull())
            .error(R.mipmap.ic_launcher)
            .into(holder.productImage)

        holder.productName.text = currentItem.title

        // Locale-specific currency formatting
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US) // Change Locale.US as needed
        val formattedPrice = currencyFormat.format(currentItem.price)
        holder.productPrice.text = formattedPrice
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
    }
}
