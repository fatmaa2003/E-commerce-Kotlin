package com.example.e_commercekotlin.data.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("statusCode") val statusCode: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("products") val products: List<Product>?
)

data class Product(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("price") val price: Double?,
    @SerializedName("stock") val stock: Int?,
    @SerializedName("categoryId") val categoryId: Int?,
    @SerializedName("categoryName") val categoryName: String?,
    @SerializedName("mainImageUrl") val mainImageUrl: String?
)
