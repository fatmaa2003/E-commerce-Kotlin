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
import com.example.e_commercekotlin.data.model.ProductResponse
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
    private var productList: List<ProductResponse.ProductResponseItem> = listOf()

    fun setProductList(productList: List<ProductResponse.ProductResponseItem>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    //set on click listner for the api integration
    fun setListener(onProductClick: ClickListener) {
        this.onProductClick = onProductClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productList[position]

        holder.productName.text = currentItem.productName
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
        val formattedPrice = currencyFormat.format(currentItem.price)
        holder.productPrice.text = formattedPrice

        Glide.with(holder.productImage.context).load(currentItem.imageUrl).into(holder.productImage)

        holder.productLayout.setOnClickListener {
            currentItem.let { onProductClick?.onProductClick(
                productId = it.productId?.toLong() ?: 0,
                productImage = it.imageUrl.orEmpty(),
                productName = it.productName.orEmpty()
            ) }
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productLayout: LinearLayout = itemView.findViewById(R.id.product_layout)
    }

    // this is the interface of the api integration
    interface ClickListener {
        fun onProductClick(productId: Long, productName : String, productImage : String)
    }

    var onProductClick: ClickListener? = null

}
