package com.example.e_commercekotlin.presentation.adapter

import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.model.Category
import com.example.e_commercekotlin.data.model.ProductDetailsDto
import com.squareup.picasso.Picasso
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
    fun setListener(onProductClick : ClickListener){
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

//       Glide.with(holder.productName.context).load(currentItem.imageUrl).into(holder.productImage)
//        Glide.with(holder.productName.context)
//            .`as`(PictureDrawable::class.java)
//            .load(currentItem.imageUrl)
//            .into(holder.productImage)

        Glide.with(holder.productImage.context).load(currentItem.imageUrl).into(holder.productImage)


        holder.productLayout.setOnClickListener {
        currentItem.productId?.let { onProductClick?.onProductClick(it.toLong())

            }
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val productLayout : LinearLayout = itemView.findViewById(R.id.product_layout)
    }
   // this is the interface of the api integration
   interface ClickListener{
       fun onProductClick(productId : Long)
   }

    var onProductClick : ClickListener?=null

}
