package com.example.e_commercekotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.dp
import com.example.e_commercekotlin.data.model.ProductResponse
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    private var productList: List<ProductResponse.ProductResponseItem> = listOf()
    private var filteredProductList: List<ProductResponse.ProductResponseItem> = productList

    var onProductClick: ClickListener? = null
    private var isFullWidth: Boolean = true

    fun setProductList(productList: List<ProductResponse.ProductResponseItem>) {
        this.productList = productList
        filteredProductList = productList
        notifyDataSetChanged()
    }

    fun setListener(onProductClick: ClickListener) {
        this.onProductClick = onProductClick
    }

    // Method to set the width dynamically
    fun setFullWidth(fullWidth: Boolean) {
        isFullWidth = fullWidth
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = filteredProductList[position]


        holder.productName.text = currentItem.productName
        val priceAsDouble = currentItem.price?.toDouble() ?: 0.0 // Default to 0.0 if conversion fails
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
        val formattedPrice = currencyFormat.format(priceAsDouble)
        holder.productPrice.text = formattedPrice


        Glide.with(holder.productImage.context)
            .load(currentItem.imageUrl)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(holder.productImage)


        val layoutParams = holder.itemView.layoutParams as ViewGroup.LayoutParams
        layoutParams.width = if (isFullWidth) {
            ViewGroup.LayoutParams.MATCH_PARENT
        } else {
            160.dp
        }
        holder.itemView.layoutParams = layoutParams

        holder.productLayout.setOnClickListener {
            currentItem.let {
                onProductClick?.onProductClick(
                    productId = it.productId?.toLong() ?: 0,
                    productImage = it.imageUrl.orEmpty(),
                    productName = it.productName.orEmpty()
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredProductList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productLayout: LinearLayout = itemView.findViewById(R.id.product_layout)
    }

    fun filter(query: String) {
        val lowerCaseQuery = query.lowercase()

        filteredProductList = if (query.isEmpty()) {
            productList
        } else {
            productList.filter { product ->
                product.productName?.lowercase()?.contains(lowerCaseQuery) == true
            }
        }
        notifyDataSetChanged()
    }

    interface ClickListener {
        fun onProductClick(productId: Long, productName: String, productImage: String)
    }
}
