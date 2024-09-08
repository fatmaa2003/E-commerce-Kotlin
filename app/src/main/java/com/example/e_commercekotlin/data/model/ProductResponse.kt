package com.example.e_commercekotlin.data.model


import com.google.gson.annotations.SerializedName

class ProductResponse : ArrayList<ProductResponse.ProductResponseItem>(){
    data class ProductResponseItem(
        @SerializedName("description")
        val description: String?,
        @SerializedName("imageUrl")
        val imageUrl: String?,
        @SerializedName("manufacturer")
        val manufacturer: String?,
        @SerializedName("price")
        val price: Double?,
        @SerializedName("productId")
        val productId: Int?,
        @SerializedName("productImages")
        val productImages: List<ProductImage?>?,
        @SerializedName("productName")
        val productName: String?,
        @SerializedName("stockQuantity")
        val stockQuantity: Int?,
        @SerializedName("warrantyPeriod")
        val warrantyPeriod: Int?
    ) {
        data class ProductImage(
            @SerializedName("imageId")
            val imageId: Int?,
            @SerializedName("imageUrl")
            val imageUrl: Any?
        )
    }
}