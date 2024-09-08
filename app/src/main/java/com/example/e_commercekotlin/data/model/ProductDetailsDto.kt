package com.example.e_commercekotlin.data.model

import com.google.gson.annotations.SerializedName

data class ProductDetailsDto(
    @SerializedName("message")
    val message: String?,
    @SerializedName("products")
    val products: List<Product?>?,
    @SerializedName("statusCode")
    val statusCode: Int?
) {
    data class Product(
        @SerializedName("categoryId")
        val categoryId: Int?,
        @SerializedName("categoryName")
        val categoryName: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("imageUrls")
        val imageUrls: List<String?>?,
        @SerializedName("mainImageUrl")
        val mainImageUrl: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("price")
        val price: Double?,
        @SerializedName("stock")
        val stock: Int?
    )
}