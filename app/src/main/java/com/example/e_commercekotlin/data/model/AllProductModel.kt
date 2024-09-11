package com.example.e_commercekotlin.data.model


import com.google.gson.annotations.SerializedName

data class AllProductModel(
    @SerializedName("message")
    val message: String?,
    @SerializedName("products")
    val products: List<ProductResponse.ProductResponseItem>?,
    @SerializedName("statusCode")
    val statusCode: Int?
)