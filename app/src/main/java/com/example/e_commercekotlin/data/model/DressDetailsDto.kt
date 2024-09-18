package com.example.e_commercekotlin.data.model


import com.google.gson.annotations.SerializedName

data class DressDetailsDto(
    @SerializedName("category")
    val category: Category?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("statusCode")
    val statusCode: Int?
) {
    data class Category(
        @SerializedName("categoryid")
        val categoryid: Int?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("discount")
        val discount: Double?,
        @SerializedName("image_url")
        val imageUrl: Any?,
        @SerializedName("marketImage")
        val marketImage: Any?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("products")
        val products: List<ProductResponse.ProductResponseItem>?
    ) {
        data class Product(
            @SerializedName("description")
            val description: String?,
            @SerializedName("freshCollection")
            val freshCollection: Boolean?,
            @SerializedName("freshSince")
            val freshSince: Any?,
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
                val imageUrl: String?
            )
        }
    }
}