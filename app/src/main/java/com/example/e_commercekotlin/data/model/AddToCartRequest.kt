package com.example.e_commercekotlin.data.model

import com.google.gson.annotations.SerializedName

data class AddToCartRequest(
    @SerializedName("products")
    val products : List<Product>

){
    data class Product(
        @SerializedName("productId")
        val productId: Long,
        @SerializedName("quantity")
        val quantity: Int
    )
}


